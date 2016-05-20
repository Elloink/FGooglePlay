package com.example.gooleplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.holder.BaseHolder;
import com.example.gooleplay.holder.DetailFooterHolder;
import com.example.gooleplay.holder.DetailHeadPHolder;
import com.example.gooleplay.holder.DetailMHPHolder;
import com.example.gooleplay.holder.DetailMLPHolder;

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
		return 4;
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
		case DETAIL_HEAD: // 创建顶部的View（应用内容）
			itemView = View.inflate(mContext, R.layout.item_detail_head, null);
			holder = new DetailHeadPHolder(itemView, mContext);
			break;
		case DETAIL_MH: // 创建中间偏上部分的View（应用情况）
			itemView = View.inflate(mContext, R.layout.item_detail_mid_high, null);
			holder = new DetailMHPHolder(itemView, mContext);
			break;
		case DETAIL_ML: // 创建中间偏下部分的View（应用截图显示）
			HorizontalScrollView horizontalScrollView = new HorizontalScrollView(
					mContext);
			ViewGroup.LayoutParams layoutParams = new LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			horizontalScrollView.setLayoutParams(layoutParams);
			itemView = horizontalScrollView;
			holder = new DetailMLPHolder(itemView, mContext);
			break;
		case DETAIL_BOTTOM: // 创建应用下部分的View（应用简介）
			itemView = View.inflate(mContext, R.layout.item_detail_footer, null );
			holder = new DetailFooterHolder(itemView, mContext);
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
