package com.example.gooleplay.bean;

public class SubjectBean {
	private String des;
	private String url;
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public SubjectBean(String des, String url) {
		super();
		this.des = des;
		this.url = url;
	}
	@Override
	public String toString() {
		return "SubjectBean [des=" + des + ", url=" + url + "]";
	}
	
	
}
