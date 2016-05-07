package com.example.gooleplay.fragment;

import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.DetailRecyclerAdapter;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.DetailProtocol;

public class DetailFragment extends BaseFragment {
	private String packageName;
	private AppDataBean data;

	public DetailFragment(String packageName) {
		super();
		this.packageName = packageName;
	}

	@Override
	protected View createSuccessView() {
		View rootView = View.inflate(getActivity(), R.layout.fragment_detail,
				null);
		RecyclerView rvContent = (RecyclerView) rootView.findViewById(R.id.rv_content);
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
		rvContent.setLayoutManager(mLayoutManager);
		rvContent.setAdapter(new DetailRecyclerAdapter(data,
				getActivity()));

		return rootView;
	}

	/**
	 * 此方法运行在子线层中可以顺便调用
	 */
	@Override
	protected int getStateAndDataFromService() {

		DetailProtocol detailProtocol = new DetailProtocol(
				packageName);
		AppDataBean load = detailProtocol.load(-1);
		SystemClock.sleep(500);
		if(load == null) {
			return PageStateCode.STATE_ERROR;
		} else {
			data = load;
			return PageStateCode.STATE_SUCCESS;
		}
	}

}
