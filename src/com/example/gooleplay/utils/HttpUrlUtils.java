package com.example.gooleplay.utils;

/**
 * ���ڻ�ȡ����·���Ĺ�����
 * @author admin
 *
 */
public class HttpUrlUtils {
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
		//http://127.0.0.1:8090/home?index=" + index
		return HOME_URL + key + "?index=" + index; //"http://127.0.0.1:8090/home?index=" + index;
	}
}
