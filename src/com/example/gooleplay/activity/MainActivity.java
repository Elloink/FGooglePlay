package com.example.gooleplay.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.gooleplay.R;
import com.example.gooleplay.fragment.BaseFragment;
import com.example.gooleplay.fragment.FragmentFactory;
import com.example.gooleplay.fragment.LeftMenuFragment;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements
		OnQueryTextListener {

	private DrawerLayout mDrawerLayout;	
	ActionBarDrawerToggle mDrawerToggle;
	private String[] fragmentTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//获取到各个Fragment的title
		fragmentTitles = getResources().getStringArray(R.array.tab_names);
		
		initUI();
		initActionBar();	//初始化ActionBar
		initLeftMenu();	//初始化侧边栏
	}
	
	
	//初始化侧边栏
	private void initLeftMenu() {
		//转化View
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		LeftMenuFragment lfFragment = new LeftMenuFragment();
		transaction.replace(R.id.fl_left_menu, lfFragment);
		transaction.commit();
	}

	private void initUI() {
		ViewPager vpContent = (ViewPager) findViewById(R.id.vp_content);
		vpContent.setAdapter(new MainAdapter(getSupportFragmentManager()));
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dw);
		//当滑动后，重新加载数据
		vpContent.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				//获取到界面，并执行刷新
				BaseFragment fragment = FragmentFactory.getFragment(arg0);
				//执行刷新动作
				fragment.changeViewByServiceState();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}

	//初始化ActionBar
	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setLogo(R.drawable.ic_launcher);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		//初始化ActionBarDrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(
				this,	//context	
				mDrawerLayout, 	//DrawerLayout对象
				R.drawable.ic_drawer_am, 	//图标
				R.string.open_drawer,	//标示
				R.string.close_drawer
				) {
			//当Drawer打开的时候
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				Toast.makeText(MainActivity.this, "抽屉打开了", 0).show();
			}
			
			//当Drawer关闭的时候
			@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						Toast.makeText(MainActivity.this, "抽屉关闭了", 0).show();
					}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();	//让DrawerToggle和ActionBar绑定
	}

	//初始化Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return mDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	/*
	 * 用于支持ViewPager的Adapter
	 */
	private class MainAdapter extends FragmentStatePagerAdapter {

		public MainAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.getFragment(position);
		}

		@Override
		public int getCount() {
			return fragmentTitles.length;
		}

		//需要重写这个方法，让PagerTabStrip能获得数据
		@Override
		public CharSequence getPageTitle(int position) {
			return fragmentTitles[position];
		}
	}
	

	/*
	 * 实现的搜索框的逻辑
	 */
	@Override
	public boolean onQueryTextChange(String arg0) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		Toast.makeText(this, arg0, 0).show();
		return false;
	}

	public void start(View view) {
		Intent i = new Intent(this, DetailActivity.class);
		startActivity(i);
	}

}
