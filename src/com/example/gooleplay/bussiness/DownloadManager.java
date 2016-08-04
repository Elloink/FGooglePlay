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
	 * ����״̬��
	 */
	public static final int IDLE_STATE = -1; // ����״̬
	public static final int DOWNLOADING_STATE = -2; // ��������״̬
	public static final int PAUSE_STATE = -3; // ��ͣ״̬
	public static final int DONE_STATE = -4; // �������״̬

	/*
	 * ���ڴ�ż����� keyΪ���� valueΪ��ǰ������Ӧ�ļ�����
	 */
	private static HashMap<String, List<Observer>> observables = new HashMap<String, List<Observer>>();
	// ���ڴ�����ص�task
	private static HashMap<String, Task> tasks = new HashMap<String, DownloadManager.Task>();
	// ���ڱ����ļ���״̬
	private static HashMap<String, DownloadBean> downInfos = new HashMap<String, DownloadBean>();
	private static DownloadThreadPool threadPool;
	private HttpUtils mHttpUtils;

	public static Handler threadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String pckName = (String) msg.obj;
			List<Observer> list = observables.get(pckName);
			switch (msg.what) {
			case 0:
				// ����״̬�ı����Ϣ
				for (Observer ob : list) {
					ob.onStateChange(downInfos.get(pckName).state);
				}
				break;

			case 1:
				// ���ͽ��ȸı����Ϣ
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

	// ˽�л����췽��
	private DownloadManager() {
		threadPool = DownloadThreadPool.getThreadPoolInstance(3, 4, 10);
		mHttpUtils = new HttpUtils();
	}

	/**
	 * ����ģʽ����ö���
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
	 * ע��
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
			// ˵���Ѿ���������
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
	 * ��ע��
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
	 * �������߷�����ʲô�ı�״̬�Ķ�����ʱ����ô˷���
	 * 
	 * @param packageName
	 */
	public void actionState(String packageName) {
		DownloadBean downloadBean = downInfos.get(packageName);
		Integer currState = downloadBean.state;
		if (currState == null) {
			// ˵����û�и�ֵ�����Ҳ��������״̬
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
		// ����ı���״̬
		downloadBean.state = currState;

		notifyStateChange(packageName);
		doSomething(currState, packageName);
	}

	// ���ݵ�ǰ��״̬�ı����ص�״̬
	private void doSomething(int currState, String packageName) {
		switch (currState) {
		case PAUSE_STATE:
			Task task = tasks.get(packageName);
			if (task == null) {
				return;
			} else {
				// ˵����Ҫɾ��
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
		Toast.makeText(MyApplication.getContext(), "��װAPK", Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * ����״̬�ĸı�
	 * @param pckName
	 */
	private void notifyStateChange(String pckName) {
		Message msg = threadHandler.obtainMessage(0 , pckName);
		msg.sendToTarget();
	}
	
	/**
	 * ���ѵ�ǰ�Ľ���
	 */
	public synchronized void notifyProgressChange(String packageName) {
		Message msg = threadHandler.obtainMessage(1 , packageName);
		msg.sendToTarget();
	}

	/**
	 * ��¶����磬�������ʼ����ʱ���������Ѿ���������˾�ֱ������״̬�ĸı�
	 * @param packageName
	 * @param state
	 */
	public void setState(String packageName, int state) {
		downInfos.get(packageName).state = state;
		notifyStateChange(packageName);
	}

	/*
	 * �������������Task
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

			// ��ȡ����ļ���λ��
			File file = new File(bean.filePath);
			long range;
			if (!file.exists()) {
				range = 0;
				bean.downUrl = HttpUrlUtils.getDownloadUrl(packageName, -1);
			} else {
				// ��ȡ�Ѿ������˵ĳ���
				range = file.length();
				if (range == bean.totalLength) {
					return;
				}
				bean.downUrl = HttpUrlUtils.getDownloadUrl(packageName, range);
			}
			Log.d("cube", "��ǰ��rangeΪ:" + range);

			// ȥ������������
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
					// ����ܳ���
					bean.totalLength = responseStream.getContentLength();
					Log.d("cube", "�ܳ���Ϊ :" + bean.totalLength);
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
					Log.d("cube", "��ǰTask�еĽ���Ϊ" + range + " / "
							+ bean.totalLength);
					notifyProgressChange(packageName);

					// �������ͣ�Ļ�,break
					if (bean.state == PAUSE_STATE) {
						isPause = true;// ����ͣ��
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
	 * �۲��߽ӿ�
	 * 
	 * @author admin
	 */
	public static interface Observer {
		/**
		 * ��״̬�ı��ʱ����ô˷���
		 * @param currentState
		 *            ��ǰ��״̬
		 */
		public void onStateChange(int currentState);

		/**
		 * �����ȷ����ı��ʱ�����
		 * @param currProgress
		 *            ��ǰ�Ľ���
		 * @param total
		 *            �ܽ���
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
