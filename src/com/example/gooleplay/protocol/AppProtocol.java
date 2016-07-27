package com.example.gooleplay.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gooleplay.bean.AppDataBean;

public class AppProtocol extends BaseProtocol<ArrayList<AppDataBean>> {

	/**
	 * ½âÎöÊý¾Ý
	 */
	@Override
	protected ArrayList<AppDataBean> parseData(String json) {
		ArrayList<AppDataBean> datas = null;
		try {
			JSONArray array = new JSONArray(json);
			datas = new ArrayList<AppDataBean>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				AppDataBean bean = new AppDataBean(object.getDouble("id"),
						object.getString("name"),
						object.getString("packageName"),
						object.getString("iconUrl"), Float.valueOf(object
								.getString("stars")),
						object.getDouble("size"),
						object.getString("downloadUrl"),
						object.getString("des"));
				datas.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return datas;
	}

	@Override
	protected String getKey() {
		return "app";
	}

}
