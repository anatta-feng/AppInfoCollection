package com.fxc.appinfo.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.fxc.appinfo.R;

/**
 * Description : 悬浮窗
 * Author   : fxc
 * Blog     : http://fxcdev.com
 * Date     : 2017/1/23.
 */

public class FloatView {

	private Context mContext;

	private WindowManager.LayoutParams mParams;
	private WindowManager mManager;

	private View mView;

	public FloatView(Context mContext) {
		this.mContext = mContext;
	}

	public void createFloatView() {
		mManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mParams = new WindowManager.LayoutParams();
		mView = setView();
		// 属性设置
		mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		mParams.format = PixelFormat.RGBA_8888;
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.gravity = Gravity.TOP | Gravity.RIGHT;

		mManager.addView(mView, mParams);
	}

	private View setView() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.float_view, null);
		return view;
	}

	public View getView() {
		return mView;
	}

	public void cancel() {
		if (mManager != null) {
			mManager.removeView(mView);
		}
	}
}
