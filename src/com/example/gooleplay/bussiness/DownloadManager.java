package com.example.gooleplay.bussiness;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.utils.FileUtils;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class DownloadManager {

	/*
	 * 定义状态码
	 */
	public static final int IDLE_STATE = -1; // 空闲状态
	public static final int DOWNLOADING_STATE = -2; // 正在下载状态
	public static final int PAUSE_STATE = -3; // 暂停状态
	public static final int DONE_STATE = -4; // 下载完毕状态

	/*
	 * 用于存放监听器 key为包名 value为当前包所对应的监听器
	 */
	private static HashMap<String, List<Observer>> observables = new HashMap<String, List<Observer>>();
	// 用于存放下载的task
	private static HashMap<String, Task> tasks = new HashMap<String, DownloadManager.Task>();
	// 用于保存文件的状态
	private static HashMap<String, DownloadBean> downInfos = new HashMap<String, DownloadBean>();
	private static DownloadThreadPool threadPool;
	private HttpUtils mHttpUtils;

	public static Handler threadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String pckName = (String) msg.obj;
			List<Observer> list = observables.get(pckName);
			switch (msg.what) {
			case 0:
				// 发送状态改变的消息
				for (Observer ob : list) {
					ob.onStateChange(downInfos.get(pckName).state);
				}
				break;

			case 1:
				// 发送进度改变的消息
				DownloadBean downloadInfo = downInfos.get(pckName);
				if (list.size() == 0) {
					return;
				}
				for (Observer observer : list) {
					observer.onProgressChange(downloadInfo.currentProgress,
							downloadInfo.totalLength);
				}
				break;

			default:
				break;
			}

		};
	};

	// 私有化构造方法
	private DownloadManager() {
		threadPool = DownloadThreadPool.getThreadPoolInstance(3, 4, 10);
		mHttpUtils = new HttpUtils();
	}

	/**
	 * 单例模式，获得对象
	 * 
	 * @return
	 */
	public static DownloadManager getInstance() {
		return InstanceHolder.INSTANCE;
	}

	private static class InstanceHolder {
		private static final DownloadManager INSTANCE = new DownloadManager();
	}

	/**
	 * 注册
	 * 
	 * @param observer
	 * @param packageName
	 */
	public void registeObserver(Observer observer, String packageName) {
		List<Observer> observersList = observables.get(packageName);

		if (observersList == null) {
			observersList = new LinkedList<Observer>();
			DownloadBean bean = new DownloadBean(packageName);
			downInfos.put(packageName, bean);
		} else if (observersList.size() >= 1) {
			// 说明已经有任务了
			observer.onProgressChange(
					downInfos.get(packageName).currentProgress,
					downInfos.get(packageName).totalLength);
		}
		if (observersList.contains(observer)) {
			return;
		}

		observersList.add(observer);
		observables.put(packageName, observersList);

	}

	/**
	 * 反注册
	 * 
	 * @param observer
	 * @param packageName
	 */
	public void unRegisteObserver(Observer observer, String packageName) {
		List<Observer> observersList = observables.get(packageName);
		if (observersList == null)
			return;
		observersList.remove(observer);
		if (observersList.size() == 0) {
			downInfos.remove(packageName);
			observables.remove(packageName);
		}
	}

	/**
	 * 当监听者发生了什么改变状态的动作的时候调用此方法
	 * 
	 * @param packageName
	 */
	public void actionState(String packageName) {
		DownloadBean downloadBean = downInfos.get(packageName);
		Integer currState = downloadBean.state;
		if (currState == null) {
			// 说明还没有赋值情况，也就是闲置状态
			currState = IDLE_STATE;
		}
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
			installApk();
			break;
		}
		// 保存改变后的状态
		downloadBean.state = currState;

		notifyStateChange(packageName);
		doSomething(currState, packageName);
	}

	// 根据当前的状态改变下载的状态
	private void doSomething(int currState, String packageName) {
		switch (currState) {
		case PAUSE_STATE:
			Task task = tasks.get(packageName);
			if (task == null) {
				return;
			} else {
				// 说明需要删除
				threadPool.remove(task);
			}
			break;

		case DOWNLOADING_STATE:
			Task task2 = new Task(packageName);
			threadPool.execute(task2);
			tasks.put(packageName, task2);
			break;
		}
	}

	private void installApk() {
		Toast.makeText(MyApplication.getContext(), "安装APK", Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 提醒状态的改变
	 * @param pckName
	 */
	private void notifyStateChange(String pckName) {
		Message msg = threadHandler.obtainMessage(0 , pckName);
		msg.sendToTarget();
	}
	
	/**
	 * 提醒当前的进度
	 */
	public synchronized void notifyProgressChange(String packageName) {
		Message msg = threadHandler.obtainMessage(1 , packageName);
		msg.sendToTarget();
	}

	/**
	 * 暴露给外界，当界面初始化的时候如果如果已经下载完毕了就直接提醒状态的改变
	 * @param packageName
	 * @param state
	 */
	public void setState(String packageName, int state) {
		downInfos.get(packageName).state = state;
		notifyStateChange(packageName);
	}

	/*
	 * 用于下载任务的Task
	 */
	class Task implements Runnable {
		private String packageName;

		public Task(String packageName) {
			this.packageName = packageName;
		}

		@Override
		public void run() {
			DownloadBean bean = downInfos.get(packageName);
			InputStream in = null;
			FileOutputStream out = null;
			boolean isPause = false;

			// 获取存放文件的位置
			File file = new File(bean.filePath);
			long range;
			if (!file.exists()) {
				range = 0;
				bean.downUrl = HttpUrlUtils.getDownloadUrl(packageName, -1);
			} else {
				// 获取已经下载了的长度
				range = file.length();
				if (range == bean.totalLength) {
					return;
				}
				bean.downUrl = HttpUrlUtils.getDownloadUrl(packageName, range);
			}
			Log.d("cube", "当前的range为:" + range);

			// 去网络下载数据
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("range", range + "");
			try {
				ResponseStream responseStream = mHttpUtils.sendSync(
						HttpMethod.GET, bean.downUrl, params);
				// String requestUrl = responseStream.getRequestUrl();
				// System.out.println("======" + requestUrl);

				in = responseStream.getBaseStream();
				if (range == 0) {
					out = new FileOutputStream(file);
					// 获得总长度
					bean.totalLength = responseStream.getContentLength();
					Log.d("cube", "总长度为 :" + bean.totalLength);
				} else {
					// append
					out = new FileOutputStream(file, true);
				}
				byte[] buffer = new byte[2048];
				int len = (int) range;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
					range += len;
					bean.currentProgress = range;
					Log.d("cube", "当前Task中的进度为" + range + " / "
							+ bean.totalLength);
					notifyProgressChange(packageName);

					// 如果是暂停的话,break
					if (bean.state == PAUSE_STATE) {
						isPause = true;// 是暂停的
						break;
					}
				}

				if (!isPause) {
					bean.state = DONE_STATE;
					notifyStateChange(packageName);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
				} catch (Exception e) {}
			}
		}
	}

	/**
	 * 观察者接口
	 * 
	 * @author admin
	 */
	public static interface Observer {
		/**
		 * 当状态改变的时候调用此方法
		 * @param currentState
		 *            当前的状态
		 */
		public void onStateChange(int currentState);

		/**
		 * 当进度发生改变的时候调用
		 * @param currProgress
		 *            当前的进度
		 * @param total
		 *            总进度
		 */
		public void onProgressChange(double currProgress, double total);
	}

	

}

class DownloadBean {
	public String filePath;
	public String downUrl;
	public long totalLength;
	public Integer state;
	public long currentProgress;

	public DownloadBean(String packName) {
		filePath = FileUtils.getDownloadFile() + packName + ".apk";
	}
}
