package com.example.gooleplay.bean;

import java.util.ArrayList;

public class CategoryDataBean {
	public ArrayList<SingleCategoryData> datas = new ArrayList<SingleCategoryData>();

	/**
	 * ��һ�������� ���а������������� , �Լ��������ݵ����нڵ�
	 * @author admin
	 */
	public class SingleCategoryData {
		// ��ǰ�������ƣ����磺��Ϸ��Ӧ�ã�
		public String title;
		
		// �����е����ƺ�ͼ��
		public ArrayList<EachLineNameAndImg> totalLinesNameAndImages = new ArrayList<EachLineNameAndImg>();

		/**
		 * ������һ�У�3���ڵ㣩���ݵĽڵ��ͼƬ
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
