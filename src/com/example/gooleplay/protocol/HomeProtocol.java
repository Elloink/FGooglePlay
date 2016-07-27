package com.example.gooleplay.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gooleplay.bean.HomeDataBean;

/**
 * 用于主页面的协议类
 * 
 * @author admin
 *
 */
public class HomeProtocol extends BaseProtocol<ArrayList<HomeDataBean>> {

	/*
	 * 解析数据
	 */
	@Override
	protected ArrayList<HomeDataBean> parseData(String json) {
		// 存放数据的集合
		ArrayList<HomeDataBean> dataList = null;
		try {
			// 解析json数据
			JSONObject jsonObject = new JSONObject(json);
			JSONArray listArray = jsonObject.getJSONArray("list");
			dataList = new ArrayList<HomeDataBean>();
			// 将json转化为对象，并且存放到集合当中
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
	 * 获得顶部viewpager的数据
	 * @return
	 */
	public String[] getPicDatas() {
		//因为图片信息只有homelist0文件中有，所以直接传入0即可
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
