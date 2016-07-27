package com.example.gooleplay.bean;


/**
 * 下载的dataBean 
 */
public class DownloadInfo {
	//当前的进度
	public long currentProgress;
	//总的大小
	public double totalSize;
	//软件的包名
	public String packageName;
	//存放包的路径
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
