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
	 * 用于获取到当前缓存的目录名
	 * （判断内存卡是否可用，可用则存放在内存卡中，如果不可用就存放在data/data目录下）
	 * @return 返回存放缓存的文件夹
	 */
	
	public static File getCacheFile() {
		File cacheFile = null;
		//检查内存卡是否可用
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//如果可用
			//构建cache路径
			cacheFile = new File(EXTERNAL_STORAGE_PATH , "/GooglePlay/cache/datacache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			//不可用则在"data/data"目录下构建缓存文件
			cacheFile = new File(DATA_CACHE_PATH + "/homeCache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		}
		return cacheFile;
	}
	
	public static File getIconCacheFile() {
		File cacheFile = null;
		//检查内存卡是否可用
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//如果可用
			//构建cache路径
			cacheFile = new File(EXTERNAL_STORAGE_PATH , "/GooglePlay/cache/iconCache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			//不可用则在"data/data"目录下构建缓存文件
			cacheFile = new File(DATA_CACHE_PATH + "/iconCache");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		}
		return cacheFile;
	}
	
	/**
	 * 获得放置安装包的缓存路径
	 * @return
	 */
	public static File getDownloadFile() {
		File cacheFile = null;
		//检查内存卡是否可用
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//如果可用
			//构建cache路径
			cacheFile = new File(EXTERNAL_STORAGE_PATH , "/GooglePlay/cache/apk");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		} else {
			//不可用则在"data/data"目录下构建缓存文件
			cacheFile = new File(DATA_CACHE_PATH + "/apk");
			if(!cacheFile.exists()) {
				cacheFile.mkdirs();
			}
		}
		return cacheFile;
	}
}
