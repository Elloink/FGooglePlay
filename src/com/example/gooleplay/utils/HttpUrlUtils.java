package com.example.gooleplay.utils;

import android.util.Log;

/**
 * ���ڻ�ȡ����·���Ĺ�����
 * @author admin
 *
 */
public class HttpUrlUtils {
	private static final String DEBUG_TAG = "HttpUrlUtils";
	private static final String HOME_URL = "http://127.0.0.1:8090/";
	/**
	 * ��ȡ��ͼƬ������
	 * @param imageName
	 * @return
	 */
	public static String getImageUrl(String imageName) {
		// http://127.0.0.1:8090/image?name=app/com.youyuan.yyhl/icon.jpg
		return HOME_URL + "image?name=" + imageName;
	}
	
	public static String getDataUrl(String key , int index) {
		//�����������ϸ��Ϣ��ʱ��
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
		Log.d(DEBUG_TAG, "��ȡ������������Ϊ��" + HOME_URL + "download?name=" + packageName + "&range=" + currentProgress);
		return HOME_URL + "download?name=" + packageName + "&range=" + currentProgress;
	}
}
