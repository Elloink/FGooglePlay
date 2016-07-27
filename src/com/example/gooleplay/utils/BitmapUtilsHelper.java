package com.example.gooleplay.utils;

import com.example.gooleplay.gloable.MyApplication;
import com.lidroid.xutils.BitmapUtils;

public class BitmapUtilsHelper {
	private static BitmapUtils utils = null;

	// public static synchronized BitmapUtils getBitmapUtils() {
	// if (utils == null) {
	// utils = new BitmapUtils(MyApplication.getContext(),
	// FileUtils.getIconCacheFile().toString() , 0.3f);
	// }
	// return utils;
	// }

	/**
	 * 实现BitmapUtils的单例实现
	 * @return
	 */
	public static BitmapUtils getInstance() {
		if (utils == null) {
			synchronized (BitmapUtilsHelper.class) {
				if (utils == null)
					utils = new BitmapUtils(MyApplication.getContext(),
							FileUtils.getIconCacheFile().toString(), 0.3f);
			}
		}
		return utils;
	}
}
