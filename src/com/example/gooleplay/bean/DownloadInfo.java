package com.example.gooleplay.bean;


/**
 * ���ص�dataBean 
 */
public class DownloadInfo {
	//��ǰ�Ľ���
	public long currentProgress;
	//�ܵĴ�С
	public double totalSize;
	//����İ���
	public String packageName;
	//��Ű���·��
	public String apkFile;
	public String downUrl;
	public void setCurrentProgress(long currentProgress) {
		this.currentProgress = currentProgress;
	}
	public void setTotalSize(double totalSize) {
		this.totalSize = totalSize;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public void setApkFile(String apkFile) {
		this.apkFile = apkFile;
	}
	
	
}
