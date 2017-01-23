package com.fxc.appinfo.file;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.fxc.appinfo.util.AppInfoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author   : fxc
 * Blog     : http://fxcdev.com
 * Date     : 2017/1/22.
 */

public class FileUtil {

	private AppInfoUtils utils;
	private Context mContext;
	private static FileUtil mFileUtil;

	private Thread mThread;

	public static boolean isCheck = true;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					Toast.makeText(mContext, "开始写入", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(mContext, "写入完成", Toast.LENGTH_SHORT).show();
					Toast.makeText(mContext, "记录完毕,请到 /data/data/com.fxc.appinfo/appInfo.txt 路径下取出文件", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	private FileUtil(Context context) {
		mContext = context;
	}

	public static FileUtil newInstance(Context context) {
		if (mFileUtil == null) {
			synchronized (FileUtil.class) {
				if (mFileUtil == null) {
					mFileUtil = new FileUtil(context);
				}
			}
		}
		return mFileUtil;
	}

	public void getFile() {
		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				utils = AppInfoUtils.newInstance(mContext);
				File file = mContext.getFileStreamPath("appInfo.txt");
				List<String> list = getTableTitle();
				while (isCheck) {
					list = addAppInfo(list);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					handler.sendEmptyMessage(1);
					FileOutputStream fs = new FileOutputStream(file);
					PrintStream p = new PrintStream(fs);
					p.print(listToString(list));
					handler.sendEmptyMessage(2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mThread.start();

	}

	public Thread getRunable() {
		return mThread;
	}

	private List<String> getTableTitle() {
		List<String> list = new ArrayList<>();
		list.add("PkgName	ActivityName	Memory");
		return list;
	}

	private List<String> addAppInfo(List<String> list) {
		list.add(utils.getTopAppPkgName() + "	" + utils.getTopActivityName() + "	" + utils.getTopAppMem());
		return list;
	}

	private String listToString(List<String> list) {
		StringBuffer sb = new StringBuffer();
		for (String line : list) {
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}
}
