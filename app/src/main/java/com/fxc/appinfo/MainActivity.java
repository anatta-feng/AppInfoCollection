package com.fxc.appinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fxc.appinfo.service.AppInfoService;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(this, AppInfoService.class);
		stopService(intent);
		startService(intent);
		finish();
	}
}
