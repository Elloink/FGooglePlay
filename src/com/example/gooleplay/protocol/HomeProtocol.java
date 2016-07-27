package com.example.gooleplay.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gooleplay.bean.HomeDataBean;

/**
 * ������ҳ���Э����
 * 
 * @author admin
 *
 */
public class HomeProtocol extends BaseProtocol<ArrayList<HomeDataBean>> {

	/*
	 * ��������
	 */
	@Override
	protected ArrayList<HomeDataBean> parseData(String json) {
		// ������ݵļ���
		ArrayList<HomeDataBean> dataList = null;
		try {
			// ����json����
			JSONObject jsonObject = new JSONObject(json);
			JSONArray listArray = jsonObject.getJSONArray("list");
			dataList = new ArrayList<HomeDataBean>();
			// ��jsonת��Ϊ���󣬲��Ҵ�ŵ����ϵ���
			for (int i = 0; i < listArray.length(); i++) {
				JSONObject data = listArray.getJSONObject(i);
				HomeDataBean homeDataBean = new HomeDataBean(
						data.getDouble("id"), data.getString("name"),
						data.getString("packageName"),
						data.getString("iconUrl"), data.getInt("stars"),
						data.getDouble("size"), data.getString("downloadUrl"),
						data.getString("des"));
				dataList.add(homeDataBean);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	/**
	 * ��ö���viewpager������
	 * @return
	 */
	public String[] getPicDatas() {
		//��ΪͼƬ��Ϣֻ��homelist0�ļ����У�����ֱ�Ӵ���0����
		String jsonString = getJsonString(0);
		return parsePicDatas(jsonString);
	}
	
	private String[] parsePicDatas(String json) {
		String[] datas = null;
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("picture");
			datas = new String[jsonArray.length()];
			for(int i = 0; i < jsonArray.length(); i++) {
				datas[i] = jsonArray.getString(i);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return datas;
	}

	@Override
	protected String getKey() {
		return "home";
	}
}
