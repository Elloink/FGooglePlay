package com.example.gooleplay.utils;

import java.io.File;

import com.example.gooleplay.gloable.MyApplication;

import android.app.Application;
import android.os.Environment;
import android.text.GetChars;

public class FileUtils {
	
	private static final String DATA_CACHE_PATH = MyApplication.getContext().getCacheDir().getAbsolutePath();
	public static String EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	/**
	 * ���ڻ�ȡ����ǰ�����Ŀ¼��
	 * ���ж��ڴ濨�Ƿ���ã������������ڴ濨�У���������þʹ����data/dataĿ¼�£�
	 * @return ���ش�Ż�����ļ���
	 */
	
	public static File getCacheFile() {
		File cacheFile = null;
		//����ڴ濨�Ƿ����
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//�������
			//����cache·��
			cacheFile = new File(EXTERNAL_STORAGE_PATH , "/GooglePlay/cache/datacache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			//����������"data/data"Ŀ¼�¹��������ļ�
			cacheFile = new File(DATA_CACHE_PATH + "/homeCache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		}
		return cacheFile;
	}
	
	public static File getIconCacheFile() {
		File cacheFile = null;
		//����ڴ濨�Ƿ����
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//�������
			//����cache·��
			cacheFile = new File(EXTERNAL_STORAGE_PATH , "/GooglePlay/cache/iconCache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			//����������"data/data"Ŀ¼�¹��������ļ�
			cacheFile = new File(DATA_CACHE_PATH + "/iconCache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		}
		return cacheFile;
	}
	
	/**
	 * ��÷��ð�װ���Ļ���·��
	 * @return
	 */
	public static File getDownloadFile() {
		File cacheFile = null;
		//����ڴ濨�Ƿ����
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//�������
			//����cache·��
			cacheFile = new File(EXTERNAL_STORAGE_PATH , "/GooglePlay/cache/apk");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			//����������"data/data"Ŀ¼�¹��������ļ�
			cacheFile = new File(DATA_CACHE_PATH + "/apk");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		}
		return cacheFile;
	}
}
