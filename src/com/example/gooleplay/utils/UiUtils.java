package com.example.gooleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;

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
	
	public static View getView(View rootView , int viewID) {
		return rootView.findViewById(viewID);
	}
	
	public static int dp2px(int dp) {
		Context context = MyApplication.getContext();
		Resources resources = context.getResources();
		float density = resources.getDisplayMetrics().density;
		return (int)(dp * density + 0.5f);
		
	}
	

}
