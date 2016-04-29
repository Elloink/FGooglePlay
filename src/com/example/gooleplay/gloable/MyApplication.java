package com.example.gooleplay.gloable;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class MyApplication extends Application {
	private static Context context;
	private static Handler handler;
	private static int mainTid;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		handler = new Handler();
		mainTid = android.os.Process.myTid();
	}

	public static Context getContext() {
		return context;
	}
	
	public static int getMainThreadId() {
		
		return mainTid;
	}
	
	public static Handler getHandler() {
		return handler;
	}

}
