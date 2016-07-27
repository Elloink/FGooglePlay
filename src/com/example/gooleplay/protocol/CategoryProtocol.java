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
		Log.d(DEBUG_TAG, "进入parseData的数据为 : " + json.substring(0 , 10));
		CategoryDataBean categoryDataBean = null;
		try {
			JSONArray rootJsonArray = new JSONArray(json);
			
			//生成一个实例
			categoryDataBean = new CategoryDataBean();
			//遍历数据，获得两个类别
			for(int i = 0; i < rootJsonArray.length(); i++) {
				//获取当前类别的JsonObject
				JSONObject singleCategoryObject = rootJsonArray.getJSONObject(i);
				
				//生成对应的数据对象
				SingleCategoryData singleCategoryData = categoryDataBean.new SingleCategoryData();
				//将title赋值进去
				singleCategoryData.title = singleCategoryObject.getString("title");
				
				//获取所有行的图标以及名字的数组
				JSONArray eachLineDataAndNameJsonArray = singleCategoryObject.getJSONArray("infos");
				//遍历进行赋值
				for(int j = 0; j < eachLineDataAndNameJsonArray.length(); j++) {
					//获得一行的图标以及名字对象
					JSONObject jsonObject = eachLineDataAndNameJsonArray.getJSONObject(j);
					//生成对应的对象
					EachLineNameAndImg eachLineNameAndImg = singleCategoryData.new EachLineNameAndImg();
					//赋值数据
					String[] names = {jsonObject.getString("name1") , jsonObject.getString("name2") , jsonObject.getString("name3")};
					String[] imgs = {jsonObject.getString("url1") , jsonObject.getString("url2") , jsonObject.getString("url3")};
					eachLineNameAndImg.images = imgs;
					eachLineNameAndImg.names = names;
					//将这一行加入到对应的类型的数组中
					singleCategoryData.totalLinesNameAndImages.add(eachLineNameAndImg);
				}
				//将单一类别加入到总的对象中
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
