package com.example.gooleplay.bussiness;

import java.util.HashMap;

import com.example.gooleplay.bussiness.Observable.Observer;

public class DownloadManager {
	private static HashMap<String, Observable> observables = new HashMap<String, Observable>();
	private static DownloadManager instance;

	// 私有化构造方法
	private DownloadManager() {
	}

	/**
	 * 单例模式，获得对象
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
	 * 注册
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
	 * 反注册
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
	 * 当监听者发生了什么改变状态的动作的时候调用此方法
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
	 * 检查此观察者的对象是否已经构建
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
