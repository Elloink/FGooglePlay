package com.example.gooleplay.bussiness;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用于下载的线程池
 * 
 * @author admin
 *
 */
public class DownloadThreadPool {
	private static int corePoolSize; // 核心线程数量
	private static int maximumPoolSize; // 最大数量
	private static long keepAliveTime; // 保活时间
	private static ThreadPoolExecutor executor;

	public static DownloadThreadPool getThreadPoolInstance(int cpSize, int mpSize,
			long kaTime) {
		corePoolSize = cpSize;
		maximumPoolSize = mpSize;
		keepAliveTime = kaTime;
		executor = InstanceHolder.threadPoolExecutor;
		return InstanceHolder.pool;
	}
	

	/**
	 * 用于执行runnable任务的方法
	 * 返回Future来控制任务
	 * @param runnable
	 * @return
	 */
	public Future<?> execute(Runnable runnable) {
		if (runnable == null) {
			return null;
		}
		// 判断是否已经创建了线程池
		if (executor == null || executor.isShutdown() || executor.isTerminated()) {
			executor = new ThreadPoolExecutor(
					corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>());
		}
		
		return executor.submit(runnable);
	}
	
	public void remove(Runnable run ) {
		executor.getQueue().remove(run);
	}
	
	/**
	 * 防止多线程情况下生成多个线程池
	 * @author admin
	 *
	 */
	private static class InstanceHolder {
		public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		public static DownloadThreadPool pool = new DownloadThreadPool();
	}
}
