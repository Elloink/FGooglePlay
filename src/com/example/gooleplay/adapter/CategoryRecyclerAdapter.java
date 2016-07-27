package com.example.gooleplay.adapter;

import java.util.ArrayList;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.CategoryDataBean.SingleCategoryData;
import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.holder.CategroyViewHolder;
import com.example.gooleplay.utils.UiUtils;

/**
 * 用于分类界面的Adapter
 * 这个Adapter因为不同于之前的Adapter所以单独写，并不继承与BaseAdapter
 * @author admin
 *
 */
public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategroyViewHolder> {
	private String DEBUG_TAG = "CategoryRecyclerAdapter";
	private ArrayList<SingleCategoryData> mDatas;
	//正常的item
	public static final int NORMAL_ITEM = -1;
	//顶部的类别的item
	public static final int CATEGROY_ITEM = -2;
	

	public CategoryRecyclerAdapter(ArrayList<SingleCategoryData> mDatas) {
		//保存数据
		this.mDatas = mDatas;
	}

	@Override
	public int getItemCount() {
		//两个item加上一共的行数
		return 1 + 1 + mDatas.get(0).totalLinesNameAndImages.size() + mDatas.get(1).totalLinesNameAndImages.size();
	}

	@Override
	public void onBindViewHolder(CategroyViewHolder viewHolder, int position) {
		
		viewHolder.bindDataWithView(position , mDatas);
	}

	@Override
	public CategroyViewHolder onCreateViewHolder(ViewGroup root, int viewType) {
		CategroyViewHolder viewHolder = null;
		View view = null;
		//根据不同的ViewType返回不同的view
		switch (viewType) {
		case NORMAL_ITEM:
			view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_category_content, root, false);
			break;
		case CATEGROY_ITEM:
			TextView textView = new TextView(MyApplication.getContext());
			textView.setTextSize(UiUtils.dp2px(10));
			textView.setTextColor(Color.BLACK);
			textView.setPadding(UiUtils.dp2px(5), UiUtils.dp2px(10), 0, UiUtils.dp2px(5));
			view = textView;
			break;
		}
		//创建ViewHolder
		viewHolder = new CategroyViewHolder(view, viewType);
		return viewHolder;
	}
	
	/*
	 * 根据postion来返回类别
	 */
	@Override
	public int getItemViewType(int position) {
		//第一个类别的子列的数量
		int size = mDatas.get(0).totalLinesNameAndImages.size();
		if(position == 0 || position == size + 1) {
			return CATEGROY_ITEM;
		} else {
			return NORMAL_ITEM;
		}
	}

}
