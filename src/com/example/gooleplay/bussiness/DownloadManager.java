package com.example.gooleplay.bussiness;

import java.util.HashMap;

import com.example.gooleplay.bussiness.Observable.Observer;

public class DownloadManager {
	private static HashMap<String, Observable> observables = new HashMap<String, Observable>();
	private static DownloadManager instance;

	// ˽�л����췽��
	private DownloadManager() {
	}

	/**
	 * ����ģʽ����ö���
	 * 
	 * @return
	 */
	public static DownloadManager getInstance() {
		if (instance == null) {
			synchronized (DownloadManager.class) {
				if (instance == null) {
					instance = new DownloadManager();
				}
			}
		}
		return instance;
	}

	/**
	 * ע��
	 * 
	 * @param observer
	 * @param packageName
	 */
	public void registeObserver(Observer observer, String packageName) {
		Observable observable = observables.get(packageName);
		if (observable != null) {
			observable.registeObserver(observer);
		}
	}

	/**
	 * ��ע��
	 * 
	 * @param observer
	 * @param packageName
	 */
	public void unRegisteObserver(Observer observer, String packageName) {
		Observable observable = observables.get(packageName);
		observable.unRegisteObserver(observer);
	}

	public void createObservable(String downloadUrl, String pckName) {
		Observable observable = observables.get(pckName);
		if (observable == null) {
			observable = new Observable(downloadUrl, pckName);
		}
		observables.put(pckName, observable);
	}

	/**
	 * �������߷�����ʲô�ı�״̬�Ķ�����ʱ����ô˷���
	 * 
	 * @param packageName
	 */
	public void actionState(String packageName) {
		Observable observable = observables.get(packageName);
		if (observable != null) {
			observable.changeState();
		}
	}

	public int getState(String packageName) {
		Observable observable = observables.get(packageName);
		if (observable != null) {
			return observable.currState;
		}
		return 0;
	}

	/**
	 * ���˹۲��ߵĶ����Ƿ��Ѿ�����
	 * 
	 * @param packageName
	 * @return
	 */
	public boolean check(String packageName) {
		Observable observable = observables.get(packageName);
		if (observable == null) {
			return false;
		}
		return true;
	}
}
