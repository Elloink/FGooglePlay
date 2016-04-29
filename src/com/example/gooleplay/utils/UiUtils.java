package com.example.gooleplay.utils;

import android.os.Handler;

import com.example.gooleplay.gloable.MyApplication;

public class UiUtils {

	public static void runOnUiThread(Runnable runnable) {
		//判断是否在主线程中运行
		if(android.os.Process.myTid() == MyApplication.getMainThreadId()) {
			//在主线程中，直接更改界面
			runnable.run();
		} else {
			//没有在主线程中，使用handler来完成
			Handler handler = MyApplication.getHandler();
			handler.post(runnable);
		}
	}
	

}
