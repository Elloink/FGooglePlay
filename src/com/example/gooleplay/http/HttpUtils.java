package com.example.gooleplay.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	/**
	 * 用于进行网络请求
	 * 
	 * @param requestUrl
	 *            请求的路径
	 * @return 返回得到的json数据
	 * @throws IOException
	 */
	public static String request4Data(String requestUrl) throws IOException {
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			// 建立连接
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);

			if (connection.getResponseCode() == 200) {
				// 连接成功
				inputStream = connection.getInputStream();
				// 获取数据
				br = new BufferedReader(new InputStreamReader(inputStream));
				StringBuilder sb = new StringBuilder();
				String temp = "";
				while ((temp = br.readLine()) != null) {
					// 构建数据
					sb.append(temp + "\r\n");
				}
				// 返回JSON值
				return sb.toString();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return null;
	}

}
