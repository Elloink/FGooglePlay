package com.example.gooleplay.bean;

public class HomeDataBean {
//	"id": 1525490,
//    "name": "有缘网",
//    "packageName": "com.youyuan.yyhl",
//    "iconUrl": "app/com.youyuan.yyhl/icon.jpg",
//    "stars": 4,
//    "size": 3876203,
//    "downloadUrl": "app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
//    "des": "产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、"
	private double id;
	private String name;
	private String packageName;
	private String iconUrl;
	private int stars;
	private double size;
	private String downloadUrl;
	private String des;
	
	
	public HomeDataBean(double id, String name, String packageName,
			String iconUrl, int stars, double size, String downloadUrl,
			String des) {
		super();
		this.id = id;
		this.name = name;
		this.packageName = packageName;
		this.iconUrl = iconUrl;
		this.stars = stars;
		this.size = size;
		this.downloadUrl = downloadUrl;
		this.des = des;
	}
	
	@Override
	public String toString() {
		return "HomeDataBean [name=" + name + ", des=" + des + "]";
	}

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
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
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
	
}
