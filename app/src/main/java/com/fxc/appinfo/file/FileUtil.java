package com.fxc.appinfo.file;

import android.content.Context;

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

	public static boolean isCheck = true;

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
		new Thread(new Runnable() {
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
					FileOutputStream fs = new FileOutputStream(file);
					PrintStream p = new PrintStream(fs);
					p.print(listToString(list));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

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
