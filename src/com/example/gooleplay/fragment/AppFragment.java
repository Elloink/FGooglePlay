package com.example.gooleplay.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gooleplay.activity.DetailActivity;
import com.example.gooleplay.adapter.AppRecyclerViewAdapter;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.AppProtocol;

/**
 * ��ҳFragment
 * 
 * @author admin
 *
 */
public class AppFragment extends BaseFragment {
	private ArrayList<AppDataBean> dataList;

	/* ���ɹ���ȡ���ݵ�ʱ�򣬹����ɹ�ҳ�� */
	@Override
	protected View createSuccessView() {

		RecyclerView recyclerView = new RecyclerView(getActivity());
		AppRecyclerViewAdapter adapter = new AppRecyclerViewAdapter(dataList,
				getActivity());
		recyclerView.setAdapter(adapter);
		
		adapter.setOnItemClickListener(new com.example.gooleplay.adapter.BaseRecyclerViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View v, int position) {
				int realPosition = position - 1;
				// �������¼������Ҵ��ݱ�����������
				handleOnClickEvent(realPosition , dataList.get(realPosition).getPackageName());
			}
		});
		
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return recyclerView;
	}

	/**
	 * ��ȡ���ݴӷ�����
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
	
	//�������¼�,ʵ�ֵ�������һ��ҳ��
		private void handleOnClickEvent(int positon, String packageName) {
			Intent intent = new Intent(getActivity() , DetailActivity.class);
			intent.putExtra("pkg", packageName);
			startActivity(intent);
		}

}
