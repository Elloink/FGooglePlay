package com.example.gooleplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.DetailRecyclerAdapter;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.fragment.DetailFragment;
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

	@ViewInject(R.id.fl_container)
	FrameLayout flContainer;

	private DetailFragment detailFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_app_detail);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// ��ø������
		ViewUtils.inject(this);

		// ��ô��ݹ�����Ӧ�ð���
		Intent intent = getIntent();
		String packageName = intent.getStringExtra("pkg");

		// ��Fragment���滻����
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		detailFragment = new DetailFragment(packageName);
		transaction.replace(R.id.fl_container, detailFragment);
		transaction.commit();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// ע��ˢ��fragment��λ����onStart��Ȼ��Ϊû�д����ò��֣��ᱨ��ָ��
		detailFragment.changeViewByServiceState();
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
