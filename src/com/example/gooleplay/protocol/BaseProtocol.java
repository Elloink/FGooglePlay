package com.example.gooleplay.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.http.HttpUtils;
import com.example.gooleplay.utils.FileUtils;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.example.gooleplay.utils.QuiteClose;

public abstract class BaseProtocol<Data> {
	public Data load(int index) {
		// 从本地获取json
		String json = loadDataFromLocal(index);
		if (json == null) {
			json = loadDataFromServer(index);
		}

		return json == null ? null : parseData(json);
	}

	public String getJsonString(int index) {
		// 从本地获取json
		String json = loadDataFromLocal(index);
		if (json == null) {
			json = loadDataFromServer(index);
		}
		return json;
	}

	/**
	 * 从服务器获取数据
	 * 
	 * @param index
	 *            当前请求页数
	 * @return 返回获取到的json字符串
	 */
	private String loadDataFromServer(int index) {
		String data = null;
		String requestURL = getSpecialRequestURL();
		// 构建请求地址
		if (requestURL == null) {
			requestURL = HttpUrlUtils.getDataUrl(getKey(), index);
		}
		System.out.println("requestURL + : " + requestURL);
		try {
			data = HttpUtils.request4Data(requestURL);
			if (data != null && !data.equals("")) {
				// 数据不为空或者不为不存在的时候，保存一份到本地中
				saveJsonToLocal(data, index);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 获得特殊的请求链接 默认为null，如果不为空表示有特殊链接的请求
	 * 
	 * @return
	 */
	protected String getSpecialRequestURL() {
		return null;
	}

	/**
	 * 保存json数据到本地
	 * 
	 * @param json
	 *            获取到的json数据
	 * @param index
	 *            当前页码
	 */
	private void saveJsonToLocal(String json, int index) {
		// 处理数据（加上时间戳）,组建新的数据
		String theOutTime = (System.currentTimeMillis() + 1000 * 180) + ""; // 超出3分钟则过期
		StringBuilder sb = new StringBuilder(theOutTime);
		sb.append("\r\n"); // 进行换行
		sb.append(json);

		// 创建缓存路径
		File cacheFile;
		if (index == -1) {
			cacheFile = new File(FileUtils.getCacheFile(), getKey());
		} else {
			cacheFile = new File(FileUtils.getCacheFile(), getKey() + "_"
					+ index);
		}
		System.out.println("cacheFiel is " + cacheFile);
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(cacheFile);
			fileWriter.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从本地获取缓存好的数据
	 * 
	 * @param index
	 *            当前请求的页数
	 * @return 返回获取到的json字符串
	 */
	private String loadDataFromLocal(int index) {
		File cacheFile;
		// 获取到缓存文件
		if (index == -1) {
			cacheFile = new File(FileUtils.getCacheFile(), getKey());
		} else {
			cacheFile = new File(FileUtils.getCacheFile(), getKey() + "_"
					+ index);
		}
		// 判断是否存在
		if (!cacheFile.exists()) {
			return null;
		}
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					cacheFile)));
			// 获取到时间戳
			String temp = br.readLine();
			// 检验是否过期
			if (System.currentTimeMillis() > Double.parseDouble(temp)) {
				// 已经过期了所以返回Null,表示不从本地种获取
				return null;
			}
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				sb.append("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			QuiteClose.quiteClose(br);
		}
		return sb.toString();
	}

	/**
	 * 解析获取到的json字符串，并转化为数据集
	 * 
	 * @param json
	 *            获取到的json字符串
	 * @return 结果的对象集
	 */
	protected abstract Data parseData(String json);

	/**
	 * 用于指定页面类别 方便与生成指定的请求路径
	 * 
	 * @return 当前页面的种类字符串
	 */
	protected abstract String getKey();
	
}
