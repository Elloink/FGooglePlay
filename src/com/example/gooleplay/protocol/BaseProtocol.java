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
		// �ӱ��ػ�ȡjson
		String json = loadDataFromLocal(index);
		if (json == null) {
			json = loadDataFromServer(index);
		}

		return json == null ? null : parseData(json);
	}

	public String getJsonString(int index) {
		// �ӱ��ػ�ȡjson
		String json = loadDataFromLocal(index);
		if (json == null) {
			json = loadDataFromServer(index);
		}
		return json;
	}

	/**
	 * �ӷ�������ȡ����
	 * 
	 * @param index
	 *            ��ǰ����ҳ��
	 * @return ���ػ�ȡ����json�ַ���
	 */
	private String loadDataFromServer(int index) {
		String data = null;
		String requestURL = getSpecialRequestURL();
		// ���������ַ
		if (requestURL == null) {
			requestURL = HttpUrlUtils.getDataUrl(getKey(), index);
		}
		System.out.println("requestURL + : " + requestURL);
		try {
			data = HttpUtils.request4Data(requestURL);
			if (data != null && !data.equals("")) {
				// ���ݲ�Ϊ�ջ��߲�Ϊ�����ڵ�ʱ�򣬱���һ�ݵ�������
				saveJsonToLocal(data, index);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * ���������������� Ĭ��Ϊnull�������Ϊ�ձ�ʾ���������ӵ�����
	 * 
	 * @return
	 */
	protected String getSpecialRequestURL() {
		return null;
	}

	/**
	 * ����json���ݵ�����
	 * 
	 * @param json
	 *            ��ȡ����json����
	 * @param index
	 *            ��ǰҳ��
	 */
	private void saveJsonToLocal(String json, int index) {
		// �������ݣ�����ʱ�����,�齨�µ�����
		String theOutTime = (System.currentTimeMillis() + 1000 * 180) + ""; // ����3���������
		StringBuilder sb = new StringBuilder(theOutTime);
		sb.append("\r\n"); // ���л���
		sb.append(json);

		// ��������·��
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
	 * �ӱ��ػ�ȡ����õ�����
	 * 
	 * @param index
	 *            ��ǰ�����ҳ��
	 * @return ���ػ�ȡ����json�ַ���
	 */
	private String loadDataFromLocal(int index) {
		File cacheFile;
		// ��ȡ�������ļ�
		if (index == -1) {
			cacheFile = new File(FileUtils.getCacheFile(), getKey());
		} else {
			cacheFile = new File(FileUtils.getCacheFile(), getKey() + "_"
					+ index);
		}
		// �ж��Ƿ����
		if (!cacheFile.exists()) {
			return null;
		}
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					cacheFile)));
			// ��ȡ��ʱ���
			String temp = br.readLine();
			// �����Ƿ����
			if (System.currentTimeMillis() > Double.parseDouble(temp)) {
				// �Ѿ����������Է���Null,��ʾ���ӱ����ֻ�ȡ
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
	 * ������ȡ����json�ַ�������ת��Ϊ���ݼ�
	 * 
	 * @param json
	 *            ��ȡ����json�ַ���
	 * @return ����Ķ���
	 */
	protected abstract Data parseData(String json);

	/**
	 * ����ָ��ҳ����� ����������ָ��������·��
	 * 
	 * @return ��ǰҳ��������ַ���
	 */
	protected abstract String getKey();
	
}
