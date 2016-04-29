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
	 * ÿ����ʾ��ʱ�򶼻�����������
	 */
	@Override
	public void bindViewWithData(Object data) {
		//Ĭ����ʾloading����
		setLoadingState(LOADING);
		//����adapter�������ݵļ���
		mAdapter.loadMoreData(this);
	}
	
	/**
	 * ����״̬�ı���ؽ������ʽ
	 * @param state �����״̬��ͨ��LoadHolder���ڲ��ľ�̬������ȡ
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
