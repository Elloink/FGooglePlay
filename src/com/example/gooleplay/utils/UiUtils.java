package com.example.gooleplay.utils;

import android.os.Handler;

import com.example.gooleplay.gloable.MyApplication;

public class UiUtils {

	public static void runOnUiThread(Runnable runnable) {
		//�ж��Ƿ������߳�������
		if(android.os.Process.myTid() == MyApplication.getMainThreadId()) {
			//�����߳��У�ֱ�Ӹ��Ľ���
			runnable.run();
		} else {
			//û�������߳��У�ʹ��handler�����
			Handler handler = MyApplication.getHandler();
			handler.post(runnable);
		}
	}
	

}
