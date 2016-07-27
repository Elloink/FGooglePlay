package com.example.gooleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;

public class TopProtocol extends BaseProtocol<String[]> {

	@Override
	protected String[] parseData(String json) {
		String[] result = null;
		try {
			JSONArray rootJsonArray = new JSONArray(json);
			result = new String[rootJsonArray.length()];
			for(int i = 0; i < rootJsonArray.length(); i++) {
				result[i] = rootJsonArray.getString(i);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected String getKey() {
		return "recommend";
	}

}
