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
 * ���۲����� ���ж����˹۲��߽ӿ�
 * 
 * @author admin
 *
 */
public class Observable {
	private static final String DEBUG_TAG = "Observable";
	/*
	 * ����״̬��
	 */
	public static final int IDLE_STATE = -1; // ����״̬
	public static final int DOWNLOADING_STATE = -2; // ��������״̬
	public static final int PAUSE_STATE = -3; // ��ͣ״̬
	public static final int DONE_STATE = -4; // �������״̬

	// ��ǰ�����������Ϣ
	public DownloadInfo downloadInfo;

	// ��ǰ״̬
	public int currState = IDLE_STATE;
	// �۲��߼���
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
	 * ��õ�ǰ��״̬
	 * 
	 * @return
	 */
	public int getCurrentState() {
		return currState;
	}

	/**
	 * ���ѵ�ǰ�Ľ���
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
	 * ���ѵ�ǰ�Ľ���
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
	 * �ı�״̬ �������˵�����ʱ����� �����ظı���״̬
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

	// ���ݵ�ǰ��״̬�ı����ص�״̬
	private void doSomething() {
//		Thread currentThread = Thread.currentThread();
//		System.out.println(currentThread.toString());
		switch (currState) {
		case IDLE_STATE:
			break;
		case PAUSE_STATE:
			if (handler != null) {
				// ȡ������
//				handler.cancel();
				handler.pause();
			}
			break;
		case DOWNLOADING_STATE:
//			if (handler == null) {
			System.out.println("��������·��Ϊ : " + HttpUrlUtils.getDownloadUrl(downloadInfo.downUrl, downloadInfo.currentProgress));
					handler = httpUtils.download(HttpUrlUtils.getDownloadUrl(downloadInfo.downUrl , downloadInfo.currentProgress),
							downloadInfo.apkFile + ".apk" ,true , true, new RequestCallBack<File>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {}

							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								Log.d(DEBUG_TAG, "��ǰ��current��total Ϊ" +total + ":" +  current);
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
	
	// ��װAPK
	private void installApk() {
		Toast.makeText(MyApplication.getContext(), "��ʼ��װAPK", Toast.LENGTH_SHORT).show();
	}

	/**
	 * �۲��߽ӿ�
	 * 
	 * @author admin
	 */
	public interface Observer {
		/**
		 * ��״̬�ı��ʱ����ô˷���
		 * 
		 * @param currentState
		 *            ��ǰ��״̬
		 */
		public void onStateChange(int currentState);

		/**
		 * �����ȷ����ı��ʱ�����
		 * 
		 * @param currProgress
		 *            ��ǰ�Ľ���
		 * @param total
		 *            �ܽ���
		 */
		public void onProgressChange(double currProgress, double total);
	}

}
