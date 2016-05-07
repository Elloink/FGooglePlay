package com.example.gooleplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.DetailRecyclerAdapter;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.protocol.DetailProtocol;
import com.example.gooleplay.utils.ThreadPoolManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ��������һ�����item֮�󵯳����������ҳ��
 * 
 * @author admin
 *
 */
public class DetailActivity extends ActionBarActivity {
	
	private static final String DEBUG_TAG = "DetailActivity";
	private static final int STATE_LOADING = 1;
	private static final int STATE_ERROR = 2;
	private static final int STATE_SUCCESS = 3;
	private int currentState = STATE_LOADING;
	
	
	@ViewInject(R.id.rv_content)
	RecyclerView rvContent; // װ�����ݵ�recyclerView
	@ViewInject(R.id.loading)
	View LoadingView;

	// ����handler��ת���߳�
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			AppDataBean data = (AppDataBean) msg.obj;
			if(data != null) {
				currentState = STATE_SUCCESS;
			} else {
				currentState = STATE_ERROR;
			}
			System.out.println("into the handler");
			// ��õ����ݺ󴴽�UI
			createUI(data);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_app_detail);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// ��ø������
		ViewUtils.inject(this);

		// ��ʼ������RecyclerView
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);// һ��Ҫ��һʱ�����
		rvContent.setLayoutManager(mLayoutManager);

		// ��ô��ݹ�����Ӧ�ð���
		Intent intent = getIntent();
		final String packageName = intent.getStringExtra("pkg");

		// ����������ͱ��������ʱ�����ŵ������߳��н���
		ThreadPoolManager.getTheadManager().getThreadPoolProxy()
				.execute(new Runnable() {
					@Override
					public void run() {
						// ��ȡ������
						DetailProtocol detailProtocol = new DetailProtocol(
								packageName);
						AppDataBean load = detailProtocol.load(-1);
						Log.d(DEBUG_TAG, "��õ�������Ϊ:" + load);
						// ������ͨ��Handler���ͻ����߳�
						handler.obtainMessage(0, load).sendToTarget();
					}
				});
	}

	/**
	 * ����ui
	 * 
	 * @param data
	 */
	private void createUI(AppDataBean data) {
		// ��õ�����֮���������������
		rvContent.setAdapter(new DetailRecyclerAdapter(data,
				DetailActivity.this));
	}
	
	private void changeViewByState() {
		switch (currentState) {
		case STATE_LOADING:
			LoadingView.setVisibility(View.VISIBLE);
			break;
		case STATE_ERROR:
			
			break;
		case STATE_SUCCESS:
			LoadingView.setVisibility(View.INVISIBLE);
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
