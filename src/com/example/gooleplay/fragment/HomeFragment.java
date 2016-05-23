package com.example.gooleplay.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gooleplay.R;
import com.example.gooleplay.activity.DetailActivity;
import com.example.gooleplay.adapter.HomeAdapter;
import com.example.gooleplay.bean.HomeDataBean;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.HomeProtocol;

/**
 * ��ҳFragment
 * 
 * @author admin
 *
 */
public class HomeFragment extends BaseFragment {
	private Context mContext;
	private ArrayList<HomeDataBean> mDataList;
	private static final String DEBUG_TAG = "HomeFragment";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// ��ʼ��HomeFragment
		changeViewByServiceState();
		// ��ȡ��Context
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/* �ӷ�������ȡ���ݣ�������״̬ */
	protected int getStateAndDataFromService() {
		HomeProtocol homeProtocol = new HomeProtocol();
		// ��õ�����
		mDataList = homeProtocol.load(0);
		//������ݣ�������Ӧ��״̬
		if (mDataList == null) {
			return PageStateCode.STATE_ERROR;
		}
		if (mDataList.size() > 0) {
			return PageStateCode.STATE_SUCCESS;
		} else {
			return PageStateCode.STATE_EMPTY;
		}
	}

	/* ���ؼ��سɹ����� */
	protected View createSuccessView() {
		View view = View.inflate(getActivity(), R.layout.fragment_home, null);
		RecyclerView recyclerView = (RecyclerView) view
				.findViewById(R.id.recycler_view);
		HomeAdapter adapter = new HomeAdapter(mDataList, mContext);
		recyclerView.setAdapter(adapter);
		adapter.setOnItemClickListener(new com.example.gooleplay.adapter.BaseRecyclerViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View v, int position) {
				int realPosition = position - 1;
				// �������¼������Ҵ��ݱ�����������
				handleOnClickEvent(realPosition , mDataList.get(realPosition).getPackageName());
			}
		});
		// ���ò��ֹ���
		recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		return view;
	}
	
	//�������¼�,ʵ�ֵ�������һ��ҳ��
	private void handleOnClickEvent(int positon, String packageName) {
		Intent intent = new Intent(getActivity() , DetailActivity.class);
		intent.putExtra("pkg", packageName);
		startActivity(intent);
	}
}

