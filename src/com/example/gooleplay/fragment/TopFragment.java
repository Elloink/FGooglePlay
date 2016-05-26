package com.example.gooleplay.fragment;

import android.R.layout;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.TopProtocol;
import com.example.gooleplay.view.TopViewGroup;

/**
 * Ê×Ò³Fragment
 * 
 * @author admin
 *
 */
public class TopFragment extends BaseFragment {
	private String DEBUG_TAG = "TopFragment";
	private String[] datas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	protected View createSuccessView() {
		
		ViewGroup.LayoutParams layoutParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		ScrollView scrollView = new ScrollView(MyApplication.getContext());
		scrollView.setLayoutParams(layoutParams);
		
		TopViewGroup topViewGroup = new TopViewGroup(MyApplication.getContext() ,datas);
		
		scrollView.addView(topViewGroup);
		return scrollView;
	}

	@Override
	protected int getStateAndDataFromService() {
		TopProtocol topProtocol = new TopProtocol();
		datas = topProtocol.load(0);

		if (datas == null) {
			return PageStateCode.STATE_ERROR;
		} else {
			return datas.length == 0 ? PageStateCode.STATE_EMPTY
					: PageStateCode.STATE_SUCCESS;
		}
	}

}
