package com.example.gooleplay.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.bean.AppDataBean.SafeInfo;
import com.example.gooleplay.utils.HttpUrlUtils;

/**
 * ��ȡ�����ϸ��Ϣ��Э��
 * 
 * @author admin
 *
 */
public class DetailProtocol extends BaseProtocol<AppDataBean> {
	private static final String DEBUG_TAG = "DetailProtocol";
	public String packageName;

	public DetailProtocol(String packageName) {
		if (packageName == null || packageName.equals("")) {
			throw new RuntimeException("����ı���Ϊ�ջ���Ϊ�մ�");
		}
		this.packageName = packageName;
	}

	@Override
	protected AppDataBean parseData(String json) {
		AppDataBean bean = null;
		try {
			JSONObject rootJsonObject = new JSONObject(json);
			bean = new AppDataBean(rootJsonObject.getDouble("id"),
					rootJsonObject.getString("name"), rootJsonObject.getString("packageName"),
					rootJsonObject.getString("iconUrl"), Float.valueOf(rootJsonObject
							.getString("stars")), rootJsonObject.getDouble("size"),
					rootJsonObject.getString("downloadUrl"), rootJsonObject.getString("des"));
			bean.setDownloadNum(rootJsonObject.getString("downloadNum"));
			bean.setVersion(rootJsonObject.getString("version"));
			bean.setDate(rootJsonObject.getString("date"));
			bean.setAuthor(rootJsonObject.getString("author"));
			//��ȡ��ǩ
			JSONArray safeInfoJsonArray = rootJsonObject.getJSONArray("safe");
			ArrayList<SafeInfo> safeInfos = new ArrayList<AppDataBean.SafeInfo>();
			for(int i = 0; i < safeInfoJsonArray.length(); i++) {
				SafeInfo info = bean.new SafeInfo();
				JSONObject safeInfoJsonObject = safeInfoJsonArray.getJSONObject(i);
				info.safeDes = safeInfoJsonObject.getString("safeDes");
				info.safeDesColor = safeInfoJsonObject.getInt("safeDesColor");
				info.safeDesUrl = safeInfoJsonObject.getString("safeDesUrl");
				info.safeUrl = safeInfoJsonObject.getString("safeUrl");
				safeInfos.add(info);
			}
			bean.setSafeInfos(safeInfos);
			//��ȡ��ͼ
			JSONArray screenJsonArray = rootJsonObject.getJSONArray("screen");
			String[] screens = new String[screenJsonArray.length()];
			for(int j = 0; j < screens.length; j++) {
				screens[j] = screenJsonArray.getString(j);
			}
			bean.setScreen(screens);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	protected String getKey() {
		return packageName;
	}

	// ��������·��
	@Override
	protected String getSpecialRequestURL() {
		return HttpUrlUtils.getDetailsUrl(packageName);
	}

}
