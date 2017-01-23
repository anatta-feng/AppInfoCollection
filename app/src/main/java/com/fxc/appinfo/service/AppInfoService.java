package com.fxc.appinfo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fxc.appinfo.R;
import com.fxc.appinfo.file.FileUtil;
import com.fxc.appinfo.view.FloatView;

/**
 * Description :
 * Author   : fxc
 * Blog     : http://fxcdev.com
 * Date     : 2017/1/23.
 */

public class AppInfoService extends Service implements View.OnClickListener {

	private final String TAG = AppInfoService.class.getName();

	private FileUtil mFileUtil;

	private FloatView mFloatView;
	private Button btStart;
	private Button btStop;
	private Button btKill;

	private boolean isStart = false;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		mFileUtil = FileUtil.newInstance(this);
		initView();
		initListener();
	}

	private void initListener() {
		btStart.setOnClickListener(this);
		btStop.setOnClickListener(this);
		btKill.setOnClickListener(this);
	}

	private void initView() {
		mFloatView = new FloatView(this);
		mFloatView.createFloatView();
		View view = mFloatView.getView();
		btStart = (Button) view.findViewById(R.id.bt_start);
		btStop = (Button) view.findViewById(R.id.bt_end);
		btKill = (Button) view.findViewById(R.id.bt_kill);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_start:
				if (!isStart) {
					startMonitor();
				}
				isStart = true;
				break;
			case R.id.bt_end:
				FileUtil.isCheck = false;
				isStart = false;
				break;
			case R.id.bt_kill:
				stopSelf();
				break;
		}
	}

	private void startMonitor() {
		FileUtil.isCheck = true;
		mFileUtil.getFile();
		Toast.makeText(this, "开始记录", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mFloatView.cancel();
		Process.killProcess(Process.myPid());
	}
}
