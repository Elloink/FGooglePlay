package com.example.gooleplay.bean;

import java.util.ArrayList;

public class CategoryDataBean {
	public ArrayList<SingleCategoryData> datas = new ArrayList<SingleCategoryData>();

	/**
	 * 单一类别的数据 其中包含：类别的名称 , 以及本类数据的所有节点
	 * @author admin
	 */
	public class SingleCategoryData {
		// 当前类别的名称（例如：游戏，应用）
		public String title;
		
		// 所有行的名称和图标
		public ArrayList<EachLineNameAndImg> totalLinesNameAndImages = new ArrayList<EachLineNameAndImg>();

		/**
		 * 包含了一行（3个节点）数据的节点和图片
		 * 
		 * @author admin
		 *
		 */
		public class EachLineNameAndImg {
			public String[] names = new String[3];
			public String[] images = new String[3];
		}
	}
}
