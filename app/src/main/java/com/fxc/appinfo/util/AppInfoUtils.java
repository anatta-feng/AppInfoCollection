package com.fxc.appinfo.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import java.util.List;

/**
 * Description :
 * Author   : fxc
 * Blog     : http://fxcdev.com
 * Date     : 2017/1/22.
 */

public class AppInfoUtils {

	private Context mContext;
	private ActivityManager mManager;
	private static AppInfoUtils utils;

	private AppInfoUtils(Context context) {
		mContext = context;
		mManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
	}

	public static AppInfoUtils newInstance(Context context) {
		if (utils == null) {
			synchronized (AppInfoUtils.class) {
				if (utils == null) {
					utils = new AppInfoUtils(context);
				}
			}
		}
		return utils;
	}

	public String getTopAppPkgName() {
		String pkgName = getTopActivity().getPackageName();
		return pkgName;
	}

	public String getTopActivityName() {
		String activityName = getTopActivity().getShortClassName();
		return activityName;
	}

	public float getTopAppMem() {
		int[] mPid = {getForegroundPid()};
		Debug.MemoryInfo[] memoryInfos = mManager.getProcessMemoryInfo(mPid);
		float mem = memoryInfos[0].dalvikPrivateDirty;
		return mem / 1024f;
	}

	private ComponentName getTopActivity() {
		return mManager.getRunningTasks(1).get(0).topActivity;
	}

	public int getForegroundPid() {
		List<ActivityManager.RunningAppProcessInfo> list = mManager.getRunningAppProcesses();
		Log.d("zxca", list.size() + "");
		for (ActivityManager.RunningAppProcessInfo info : list) {
			Log.d("zxca", "pid " + info.pid);
			if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE || info.importance ==
					ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return info.pid;
			}
		}
		return 0;
	}
}
