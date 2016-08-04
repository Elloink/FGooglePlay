package com.example.gooleplay.utils;

import android.util.Log;

/**
 * 用于获取请求路径的工具类
 * @author admin
 *
 */
public class HttpUrlUtils {
	private static final String DEBUG_TAG = "HttpUrlUtils";
	private static final String HOME_URL = "http://127.0.0.1:8090/";
	/**
	 * 获取到图片的链接
	 * @param imageName
	 * @return
	 */
	public static String getImageUrl(String imageName) {
		// http://127.0.0.1:8090/image?name=app/com.youyuan.yyhl/icon.jpg
		return HOME_URL + "image?name=" + imageName;
	}
	
	public static String getDataUrl(String key , int index) {
		//如果是请求详细信息的时候
		if(key  != null && key.equals(".")) {
			return getDetailsUrl(key);
		} else {
			//http://127.0.0.1:8090/home?index=" + index
			return HOME_URL + key + "?index=" + index; //"http://127.0.0.1:8090/home?index=" + index;
		}
	}
	
	public static String getDetailsUrl(String packageName) {
		return HOME_URL + "detail?packageName=" + packageName;
	}
	//http://127.0.0.1:8090/download?name=xxx.xxx.xxx&range=111" + 
	public static String getDownloadUrl(String packageName , long currentProgress) {
		if(currentProgress == -1) {
			return HOME_URL + "download?name=" + packageName;
		}
		Log.d(DEBUG_TAG, "获取到的下载链接为：" + HOME_URL + "download?name=" + packageName + "&range=" + currentProgress);
		return HOME_URL + "download?name=" + packageName + "&range=" + currentProgress;
	}
}
