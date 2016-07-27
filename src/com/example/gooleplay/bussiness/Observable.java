package com.example.gooleplay.bussiness;

import java.io.File;
import java.util.ArrayList;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.gooleplay.R.color;
import com.example.gooleplay.bean.DownloadInfo;
import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.utils.FileUtils;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * 被观察者类 其中定义了观察者接口
 * 
 * @author admin
 *
 */
public class Observable {
	private static final String DEBUG_TAG = "Observable";
	/*
	 * 定义状态码
	 */
	public static final int IDLE_STATE = -1; // 空闲状态
	public static final int DOWNLOADING_STATE = -2; // 正在下载状态
	public static final int PAUSE_STATE = -3; // 暂停状态
	public static final int DONE_STATE = -4; // 下载完毕状态

	// 当前软件的下载信息
	public DownloadInfo downloadInfo;

	// 当前状态
	public int currState = IDLE_STATE;
	// 观察者集合
	ArrayList<Observer> observers = new ArrayList<Observable.Observer>();
	//
	private HttpUtils httpUtils;
	private HttpHandler<File> handler;

	public Observable(String downloadUrl, String pckName) {
		downloadInfo = new DownloadInfo();
		downloadInfo.setPackageName(pckName);
		downloadInfo.downUrl = downloadUrl;

		File file = new File(FileUtils.getDownloadFile(), pckName);
		downloadInfo.apkFile = file.getAbsolutePath();

		httpUtils = new HttpUtils();
	}

	public void registeObserver(Observer observer) {
		if (observer == null) {
			throw new RuntimeException("observer = null");
		}
		observers.add(observer);
	}

	public void unRegisteObserver(Observer observer) {
		if (observer == null) {
			throw new RuntimeException("observer = null");
		}
		if (!observers.contains(observer)) {
			throw new RuntimeException("observer not contain in the list");
		}
		observers.remove(observer);
	}

	/**
	 * 获得当前的状态
	 * 
	 * @return
	 */
	public int getCurrentState() {
		return currState;
	}

	/**
	 * 提醒当前的进度
	 */
	public synchronized void notifyProgressChange() {
		if (observers.size() == 0) {
			return;
		}
		for (Observer observer : observers) {
			observer.onProgressChange(downloadInfo.currentProgress,
					downloadInfo.totalSize);
		}
	}

	/**
	 * 提醒当前的进度
	 */
	public synchronized void notifyStateChange() {
		if (observers.size() == 0) {
			return;
		}
		for (Observer observer : observers) {
			observer.onStateChange(currState);
		}
	}

	/**
	 * 改变状态 当发生了单击的时候调用 并返回改变后的状态
	 */
	public void changeState() {
		switch (currState) {
		case IDLE_STATE:
			currState = DOWNLOADING_STATE;
			break;
		case PAUSE_STATE:
			currState = DOWNLOADING_STATE;
			break;
		case DOWNLOADING_STATE:
			currState = PAUSE_STATE;
			break;
		case DONE_STATE:
			break;
		}

		doSomething();
		notifyStateChange();
	}

	// 根据当前的状态改变下载的状态
	private void doSomething() {
//		Thread currentThread = Thread.currentThread();
//		System.out.println(currentThread.toString());
		switch (currState) {
		case IDLE_STATE:
			break;
		case PAUSE_STATE:
			if (handler != null) {
				// 取消任务
//				handler.cancel();
				handler.pause();
			}
			break;
		case DOWNLOADING_STATE:
//			if (handler == null) {
			System.out.println("下载请求路径为 : " + HttpUrlUtils.getDownloadUrl(downloadInfo.downUrl, downloadInfo.currentProgress));
					handler = httpUtils.download(HttpUrlUtils.getDownloadUrl(downloadInfo.downUrl , downloadInfo.currentProgress),
							downloadInfo.apkFile + ".apk" ,true , true, new RequestCallBack<File>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								Log.d(DEBUG_TAG, "当前的current和total 为" +total + ":" +  current);
								long curr = downloadInfo.currentProgress;
								
								downloadInfo.setTotalSize(total);
								downloadInfo.setCurrentProgress(curr + current);
								notifyProgressChange();
							}

							@Override
							public void onSuccess(ResponseInfo<File> arg0) {
								downloadInfo
										.setCurrentProgress((long) downloadInfo.totalSize);
								currState = DONE_STATE;
								notifyStateChange();
							}
						});
//			} else {
//				handler.resume();
//			}
			break;
		case DONE_STATE:
			installApk();
			break;
		}

	}
	
//	class Tast implements Runnable {
//
//		@Override
//		public void run() {
//		}
//		
//	}
	
	// 安装APK
	private void installApk() {
		Toast.makeText(MyApplication.getContext(), "开始安装APK", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 观察者接口
	 * 
	 * @author admin
	 */
	public interface Observer {
		/**
		 * 当状态改变的时候调用此方法
		 * 
		 * @param currentState
		 *            当前的状态
		 */
		public void onStateChange(int currentState);

		/**
		 * 当进度发生改变的时候调用
		 * 
		 * @param currProgress
		 *            当前的进度
		 * @param total
		 *            总进度
		 */
		public void onProgressChange(double currProgress, double total);
	}

}
