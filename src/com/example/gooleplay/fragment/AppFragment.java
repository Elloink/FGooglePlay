package com.example.gooleplay.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gooleplay.adapter.AppRecyclerViewAdapter;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.AppProtocol;

/**
 * 首页Fragment
 * 
 * @author admin
 *
 */
public class AppFragment extends BaseFragment {
	private ArrayList<AppDataBean> dataList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/* 当成果获取数据的时候，构建成功页面 */
	@Override
	protected View createSuccessView() {

		RecyclerView recyclerView = new RecyclerView(getActivity());
		AppRecyclerViewAdapter adapter = new AppRecyclerViewAdapter(dataList,
				getActivity());
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return recyclerView;
	}

	/**
	 * 获取数据从服务器
	 */
	@Override
	protected int getStateAndDataFromService() {
		AppProtocol appProtocol = new AppProtocol();
		dataList = appProtocol.load(1);
		if (dataList == null) {
			return PageStateCode.STATE_ERROR;
		} else {
			return dataList.size() == 0 ? PageStateCode.STATE_EMPTY
					: PageStateCode.STATE_SUCCESS;
		}
	}

}
