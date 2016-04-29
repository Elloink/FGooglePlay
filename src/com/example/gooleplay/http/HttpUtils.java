package com.example.gooleplay.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	/**
	 * ���ڽ�����������
	 * 
	 * @param requestUrl
	 *            �����·��
	 * @return ���صõ���json����
	 * @throws IOException
	 */
	public static String request4Data(String requestUrl) throws IOException {
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			// ��������
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);

			if (connection.getResponseCode() == 200) {
				// ���ӳɹ�
				inputStream = connection.getInputStream();
				// ��ȡ����
				br = new BufferedReader(new InputStreamReader(inputStream));
				StringBuilder sb = new StringBuilder();
				String temp = "";
				while ((temp = br.readLine()) != null) {
					// ��������
					sb.append(temp + "\r\n");
				}
				// ����JSONֵ
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
