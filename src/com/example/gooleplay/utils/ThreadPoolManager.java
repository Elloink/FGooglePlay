package com.example.gooleplay.utils;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class ThreadPoolManager {
	/* ���õ�����ģʽ */
	private ThreadPoolManager() {
	}

	private static ThreadPoolManager instance = new ThreadPoolManager();
	private ThreadPoolProxy longPool;

	public static ThreadPoolManager getTheadManager() {
		return instance;
	}

	/**
	 * ����̳߳�
	 * 
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @return
	 */
	public synchronized ThreadPoolProxy getThreadPoolProxy() {
		if (longPool == null) {
			longPool = new ThreadPoolProxy(3, 3);
		}
		return longPool;
	}

	/**
	 * ThreadPool��ҵ����
	 * 
	 * @author admin
	 *
	 */
	public class ThreadPoolProxy {
		private int corePoolSize;
		private int maximumPoolSize;
		// private LinkedBlockingQueue<Runnable> workQueue = new
		// LinkedBlockingQueue<Runnable>(10);
		private ThreadPoolExecutor pool;

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
		}

		/**
		 * ����runnable
		 * 
		 * @param runnable
		 */
		public void execute(Runnable runnable) {
			if (pool == null) {
				// �����̳߳�
				/*
				 * 1. �̳߳����������ٸ��߳� 2. ����Ŷ�����, ����Ŀ����߳��� 3. ����̳߳�û��Ҫִ�е����� �����
				 * 4.ʱ��ĵ�λ 5 ��� �̳߳��������̶߳��Ѿ�����,ʣ�µ����� ��ʱ�浽LinkedBlockingQueue������
				 * �Ŷ�
				 */
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						5000L, TimeUnit.NANOSECONDS,
						new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable);
		}

		/**
		 * ȡ������
		 * 
		 * @param runnable
		 */
		public void cancel(Runnable runnable) {
			if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
				pool.remove(runnable);
			}
		}
	}
}
