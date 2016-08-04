package com.example.gooleplay.bussiness;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �������ص��̳߳�
 * 
 * @author admin
 *
 */
public class DownloadThreadPool {
	private static int corePoolSize; // �����߳�����
	private static int maximumPoolSize; // �������
	private static long keepAliveTime; // ����ʱ��
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
	 * ����ִ��runnable����ķ���
	 * ����Future����������
	 * @param runnable
	 * @return
	 */
	public Future<?> execute(Runnable runnable) {
		if (runnable == null) {
			return null;
		}
		// �ж��Ƿ��Ѿ��������̳߳�
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
	 * ��ֹ���߳���������ɶ���̳߳�
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
