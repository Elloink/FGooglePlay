package com.example.gooleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.gooleplay.bean.CategoryDataBean;
import com.example.gooleplay.bean.CategoryDataBean.SingleCategoryData;
import com.example.gooleplay.bean.CategoryDataBean.SingleCategoryData.EachLineNameAndImg;

public class CategoryProtocol extends BaseProtocol<CategoryDataBean>{
	
	private String DEBUG_TAG = "CategoryProtocol";

	@Override
	protected CategoryDataBean parseData(String json) {
		Log.d(DEBUG_TAG, "����parseData������Ϊ : " + json.substring(0 , 10));
		CategoryDataBean categoryDataBean = null;
		try {
			JSONArray rootJsonArray = new JSONArray(json);
			
			//����һ��ʵ��
			categoryDataBean = new CategoryDataBean();
			//�������ݣ�����������
			for(int i = 0; i < rootJsonArray.length(); i++) {
				//��ȡ��ǰ����JsonObject
				JSONObject singleCategoryObject = rootJsonArray.getJSONObject(i);
				
				//���ɶ�Ӧ�����ݶ���
				SingleCategoryData singleCategoryData = categoryDataBean.new SingleCategoryData();
				//��title��ֵ��ȥ
				singleCategoryData.title = singleCategoryObject.getString("title");
				
				//��ȡ�����е�ͼ���Լ����ֵ�����
				JSONArray eachLineDataAndNameJsonArray = singleCategoryObject.getJSONArray("infos");
				//�������и�ֵ
				for(int j = 0; j < eachLineDataAndNameJsonArray.length(); j++) {
					//���һ�е�ͼ���Լ����ֶ���
					JSONObject jsonObject = eachLineDataAndNameJsonArray.getJSONObject(j);
					//���ɶ�Ӧ�Ķ���
					EachLineNameAndImg eachLineNameAndImg = singleCategoryData.new EachLineNameAndImg();
					//��ֵ����
					String[] names = {jsonObject.getString("name1") , jsonObject.getString("name2") , jsonObject.getString("name3")};
					String[] imgs = {jsonObject.getString("url1") , jsonObject.getString("url2") , jsonObject.getString("url3")};
					eachLineNameAndImg.images = imgs;
					eachLineNameAndImg.names = names;
					//����һ�м��뵽��Ӧ�����͵�������
					singleCategoryData.totalLinesNameAndImages.add(eachLineNameAndImg);
				}
				//����һ�����뵽�ܵĶ�����
				categoryDataBean.datas.add(singleCategoryData);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return categoryDataBean;
	}

	@Override
	protected String getKey() {
		return "category";
	}

}
