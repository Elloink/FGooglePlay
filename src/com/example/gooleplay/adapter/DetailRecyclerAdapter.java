package com.example.gooleplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.holder.BaseHolder;
import com.example.gooleplay.holder.DetailHeadPHolder;

public class DetailRecyclerAdapter extends RecyclerView.Adapter {
	public static final int DETAIL_HEAD = 0;
	public static final int DETAIL_MH = 1;
	public static final int DETAIL_ML = 2;
	public static final int DETAIL_BOTTOM = 3;
	AppDataBean mData;
	private Context mContext;

	public DetailRecyclerAdapter(AppDataBean data, Context context) {
		mData = data;
		mContext = context;
	}

	@Override
	public int getItemCount() {
		return 1;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int arg1) {
		if (holder == null || !(holder instanceof BaseHolder)) {
			throw new RuntimeException("ViewHolder转换出问题");
		}
		// 进行强转，然后绑定数据
		((BaseHolder<AppDataBean>) holder).bindViewWithData(mData);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int viewType) {
		View itemView = null;
		BaseHolder<AppDataBean> holder = null;
		switch (viewType) {
		case DETAIL_HEAD:
			itemView = View.inflate(mContext, R.layout.item_detail_head, null);
			holder = new DetailHeadPHolder(itemView, mContext);
			break;
		case DETAIL_ML:

			break;
		case DETAIL_MH:

			break;
		case DETAIL_BOTTOM:

			break;
		}
		return holder;
	}

	/*
	 * 获得当前位置的item的类型 异常就返回-1
	 * 
	 * @see android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		switch (position) {
		case 0:
			return DETAIL_HEAD;
		case 1:
			return DETAIL_MH;
		case 2:
			return DETAIL_ML;
		case 3:
			return DETAIL_BOTTOM;
		}
		return -1;
	}

}
