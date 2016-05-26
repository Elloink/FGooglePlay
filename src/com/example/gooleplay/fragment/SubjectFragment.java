package com.example.gooleplay.fragment;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.SubjectRecyclerAdapter;
import com.example.gooleplay.bean.SubjectBean;
import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.SubjectProtocol;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Ê×Ò³Fragment
 * @author admin
 *
 */
public class SubjectFragment extends BaseFragment {
	private ArrayList<SubjectBean> mDatas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected View createSuccessView() {
		View view = View.inflate(getActivity(), R.layout.fragment_subject, null);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_subject);
		SubjectRecyclerAdapter adapter = new SubjectRecyclerAdapter(mDatas, getActivity());
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return view;
	}

	@Override
	protected int getStateAndDataFromService() {
		SubjectProtocol protocol = new SubjectProtocol();
		mDatas = protocol.load(0);
		if(mDatas == null) {
			return PageStateCode.STATE_ERROR;
		}
		if(mDatas.size() == 0) {
			return PageStateCode.STATE_EMPTY;
		} else {
			return PageStateCode.STATE_SUCCESS;
		}
	}
}
