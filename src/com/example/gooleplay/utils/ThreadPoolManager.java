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
	/* 采用单例的模式 */
	private ThreadPoolManager() {
	}

	private static ThreadPoolManager instance = new ThreadPoolManager();
	private ThreadPoolProxy longPool;

	public static ThreadPoolManager getTheadManager() {
		return instance;
	}

	/**
	 * 获得线程池
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
	 * ThreadPool的业务类
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
		 * 处理runnable
		 * 
		 * @param runnable
		 */
		public void execute(Runnable runnable) {
			if (pool == null) {
				// 创建线程池
				/*
				 * 1. 线程池里面管理多少个线程 2. 如果排队满了, 额外的开的线程数 3. 如果线程池没有要执行的任务 存活多久
				 * 4.时间的单位 5 如果 线程池里管理的线程都已经用了,剩下的任务 临时存到LinkedBlockingQueue对象中
				 * 排队
				 */
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						5000L, TimeUnit.NANOSECONDS,
						new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable);
		}

		/**
		 * 取消任务
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
