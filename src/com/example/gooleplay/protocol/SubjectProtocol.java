package com.example.gooleplay.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gooleplay.bean.SubjectBean;

public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectBean>> {

	/**
	 * ½âÎöÊý¾Ý
	 */
	@Override
	protected ArrayList<SubjectBean> parseData(String json) {
		ArrayList<SubjectBean> datas = null;
		if(json != null) {
			datas = new ArrayList<SubjectBean>();
			try {
				JSONArray array = new JSONArray(json);
				for(int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
					SubjectBean data = new SubjectBean(object.getString("des"), object.getString("url"));
					datas.add(data);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	@Override
	protected String getKey() {
		return "subject";
	}

}
