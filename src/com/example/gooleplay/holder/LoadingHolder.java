package com.example.gooleplay.holder;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.BaseRecyclerViewAdapter;

public class LoadingHolder extends BaseHolder {

	public static final int LOADING = 1;
	public static final int NO_MORE = 2;
	public static final int HAVA_DATA = 3;
	public static final int LOADING_ERROR = 4;
	public BaseRecyclerViewAdapter mAdapter;

	public LoadingHolder(View itemView, Context context , BaseRecyclerViewAdapter adapter) {
		super(itemView, context);
		mAdapter = adapter;
	}

	/**
	 * 每次显示的时候都会调用这个方法
	 */
	@Override
	public void bindViewWithData(Object data) {
		//默认显示loading方法
		setLoadingState(LOADING);
		//调用adapter进行数据的加载
		mAdapter.loadMoreData(this);
	}
	
	/**
	 * 根据状态改变加载界面的样式
	 * @param state 界面的状态，通过LoadHolder类内部的静态变量获取
	 */
	public void setLoadingState(int state) {
		switch (state) {
		case LOADING:
			((FrameLayout)getView(R.id.root_load)).setVisibility(View.VISIBLE);
			((LinearLayout)getView(R.id.ll_loading)).setVisibility(View.VISIBLE);
			((LinearLayout)getView(R.id.ll_no_more)).setVisibility(View.INVISIBLE);
			((LinearLayout)getView(R.id.ll_loading_error)).setVisibility(View.INVISIBLE);
			break;
		case HAVA_DATA:
			((FrameLayout)getView(R.id.root_load)).setVisibility(View.GONE);
			((LinearLayout)getView(R.id.ll_loading)).setVisibility(View.VISIBLE);
			((LinearLayout)getView(R.id.ll_no_more)).setVisibility(View.INVISIBLE);
			((LinearLayout)getView(R.id.ll_loading_error)).setVisibility(View.INVISIBLE);
			break;
		case NO_MORE:
			((FrameLayout)getView(R.id.root_load)).setVisibility(View.VISIBLE);
			((LinearLayout)getView(R.id.ll_loading)).setVisibility(View.INVISIBLE);
			((LinearLayout)getView(R.id.ll_no_more)).setVisibility(View.VISIBLE);
			((LinearLayout)getView(R.id.ll_loading_error)).setVisibility(View.INVISIBLE);
			break;
		case LOADING_ERROR :
			((FrameLayout)getView(R.id.root_load)).setVisibility(View.VISIBLE);
			((LinearLayout)getView(R.id.ll_loading)).setVisibility(View.INVISIBLE);
			((LinearLayout)getView(R.id.ll_no_more)).setVisibility(View.INVISIBLE);
			((LinearLayout)getView(R.id.ll_loading_error)).setVisibility(View.VISIBLE);
			break;
		}
	}
}
