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
 * 当单击开一个软件item之后弹出的软件详情页面
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
	RecyclerView rvContent; // 装载内容的recyclerView
	@ViewInject(R.id.loading)
	View LoadingView;

	// 创建handler来转换线程
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			AppDataBean data = (AppDataBean) msg.obj;
			if(data != null) {
				currentState = STATE_SUCCESS;
			} else {
				currentState = STATE_ERROR;
			}
			System.out.println("into the handler");
			// 获得到数据后创建UI
			createUI(data);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_app_detail);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// 获得各个组件
		ViewUtils.inject(this);

		// 初始化设置RecyclerView
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);// 一定要第一时间调用
		rvContent.setLayoutManager(mLayoutManager);

		// 获得传递过来的应用包名
		Intent intent = getIntent();
		final String packageName = intent.getStringExtra("pkg");

		// 将网络请求和本地请求耗时操作放到其他线程中进行
		ThreadPoolManager.getTheadManager().getThreadPoolProxy()
				.execute(new Runnable() {
					@Override
					public void run() {
						// 获取到数据
						DetailProtocol detailProtocol = new DetailProtocol(
								packageName);
						AppDataBean load = detailProtocol.load(-1);
						Log.d(DEBUG_TAG, "获得到的数据为:" + load);
						// 将数据通过Handler发送回主线程
						handler.obtainMessage(0, load).sendToTarget();
					}
				});
	}

	/**
	 * 创建ui
	 * 
	 * @param data
	 */
	private void createUI(AppDataBean data) {
		// 获得到数据之后才设置其适配器
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
