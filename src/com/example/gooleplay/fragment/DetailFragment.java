package com.example.gooleplay.fragment;

import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.DetailRecyclerAdapter;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.bussiness.DownloadManager;
import com.example.gooleplay.bussiness.Observable;
import com.example.gooleplay.bussiness.Observable.Observer;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.protocol.DetailProtocol;

public class DetailFragment extends BaseFragment implements OnClickListener , Observer {
	private String packageName;
	private AppDataBean data;
	private ProgressBar pbProgress;
	private DownloadManager manager;
	private Button btnDownload;

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
		
		//初始化按钮的单击事件
		Button btnCollection =(Button) rootView.findViewById(R.id.btn_collection);
		Button btnShare =(Button) rootView.findViewById(R.id.btn_share);
		btnDownload = (Button) rootView.findViewById(R.id.btn_download);
		pbProgress = (ProgressBar) rootView.findViewById(R.id.pb_progress);
		pbProgress.setMax(100);
		btnCollection.setOnClickListener(this);
		btnDownload.setOnClickListener(this);
		btnShare.setOnClickListener(this);
		
		
		manager = DownloadManager.getInstance();
		if(!manager.check(packageName)) {
			//如果是还没有创建被观察者
			manager.createObservable(data.getDownloadUrl(), packageName);
		}
		manager.registeObserver(this, packageName);

		return rootView;
	}

	/**
	 * 此方法运行在子线层中
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
			Toast.makeText(getActivity(), "收藏", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_download:
			Toast.makeText(getActivity(), "下载", Toast.LENGTH_SHORT).show();
			manager.actionState(packageName);
			break;
		case R.id.btn_share:
			Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	@Override
	public void onStateChange(int currentState) {
		System.out.println("DetailFragment接收到了发来的更新信息 " + currentState);
		
		switch (currentState) {
		case Observable.DONE_STATE:
			btnDownload.setText("安装");
			pbProgress.setProgress(100);
			break;
		case Observable.DOWNLOADING_STATE:
			break;
		case Observable.IDLE_STATE:
			btnDownload.setText("下载");
			break;
		case Observable.PAUSE_STATE:
			btnDownload.setText("暂停");
			break;

		}
	}

	@Override
	public void onProgressChange(double currProgress, double total) {
		int num1 = (int) (currProgress / total * 100);
//		btnDownload.setText(result + "%");
//		pbProgress.setProgress((int) ( Float.valueOf(result) ) );
		pbProgress.setProgress(num1);
		System.out.println("DetailFragment接收到了发来的更新信息 " + currProgress  + "/" + total);
	}
	
	class A {
		
	}

}
