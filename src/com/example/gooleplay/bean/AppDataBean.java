package com.example.gooleplay.bean;

public class AppDataBean {
//	"id": 1580615,
//    "name": "人人",
//    "packageName": "com.renren.mobile.android",
//    "iconUrl": "app/com.renren.mobile.android/icon.jpg",
//    "stars": 2,
//    "size": 21803987,
//    "downloadUrl": "app/com.renren.mobile.android/com.renren.mobile.android.apk",
//    "des": "2005-2014 你的校园一直在这儿。中国最大的实名制SNS网络平台，大学生"
	private double id;
	private String name;
	private String packageName;
	private String iconUrl;
	private float starts;
	private double size;
	private String downloadUrl;
	private String des;
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public float getStarts() {
		return starts;
	}
	public void setStarts(float starts) {
		this.starts = starts;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@Override
	public String toString() {
		return "AppDataBean [name=" + name + ", des=" + des + "]";
	}
	public AppDataBean(double id, String name, String packageName,
			String iconUrl, float starts, double size, String downloadUrl,
			String des) {
		super();
		this.id = id;
		this.name = name;
		this.packageName = packageName;
		this.iconUrl = iconUrl;
		this.starts = starts;
		this.size = size;
		this.downloadUrl = downloadUrl;
		this.des = des;
	}
	
	
	
}
