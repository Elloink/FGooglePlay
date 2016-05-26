package com.example.gooleplay.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gooleplay.adapter.CategoryRecyclerAdapter;
import com.example.gooleplay.bean.CategoryDataBean.SingleCategoryData;
import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.CategoryProtocol;

/**
 * Ê×Ò³Fragment
 * @author admin
 *
 */
public class CategoryFragment extends BaseFragment {
	private ArrayList<SingleCategoryData> mDatas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected View createSuccessView() {
		RecyclerView categoryRecyclerView = new RecyclerView(MyApplication.getContext());
		categoryRecyclerView.setAdapter(new CategoryRecyclerAdapter(mDatas));
		
		categoryRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
		
		return categoryRecyclerView;
	}

	@Override
	protected int getStateAndDataFromService() {
		
		CategoryProtocol categoryProtocol = new CategoryProtocol();
		if(categoryProtocol.load(0) == null) {
			return PageStateCode.STATE_ERROR;
		}
		mDatas = categoryProtocol.load(0).datas;
		if (mDatas == null) {
			return PageStateCode.STATE_ERROR;
		} else {
			return mDatas.size() == 0 ? PageStateCode.STATE_EMPTY
					: PageStateCode.STATE_SUCCESS;
		}
	}
	 
	 
}
