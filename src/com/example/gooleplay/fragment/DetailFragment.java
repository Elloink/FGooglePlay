package com.example.gooleplay.fragment;

import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.DetailRecyclerAdapter;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.DetailProtocol;

public class DetailFragment extends BaseFragment implements OnClickListener {
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
		
		//��ʼ����ť�ĵ����¼�
		Button btnCollection =(Button) rootView.findViewById(R.id.btn_collection);
		Button btnDownload =(Button) rootView.findViewById(R.id.btn_download);
		Button btnShare =(Button) rootView.findViewById(R.id.btn_share);
		
		btnCollection.setOnClickListener(this);
		btnDownload.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		return rootView;
	}

	/**
	 * �˷������������߲���
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_collection:
			Toast.makeText(getActivity(), "�ղ�", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_download:
			Toast.makeText(getActivity(), "����", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_share:
			Toast.makeText(getActivity(), "����", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
