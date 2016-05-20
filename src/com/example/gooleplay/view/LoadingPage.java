package com.example.gooleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.gooleplay.R;
import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.gloable.PageStateCode;
import com.example.gooleplay.utils.ThreadPoolManager;
import com.example.gooleplay.utils.UiUtils;

/**
 * 用于加载界面的FrameLayout，主要用于简化BaseFragment的代码
 * 
 * @author admin
 *
 */
public abstract class LoadingPage extends FrameLayout {

	// 当前状态
	private int currentState = PageStateCode.STATE_UNKOWN;

	// 表示几个状态的布局
	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successView;
	
	public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	/**
	 * 进行初始化的函数
	 * 一来就先将加载界面，错误界面，空界面的布局加载到Fragment中放着，因为这三个布局是静态的所以可以提前创建好
	 * 然后再根据状态来改变界面的显示
	 */
	public void init() {
		loadingView = createLoadingView();
		if (loadingView != null) {
			this.addView(loadingView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		errorView = createErrorView();
		if (errorView != null) {
			this.addView(errorView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}
		emptyView = createEmptyView();
		if (emptyView != null) {
			this.addView(emptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}
		// 根据状态显示界面
		showViewByState();
	}

	/* 
	 * 根据状态显示页面 
	 */
	private void showViewByState() {
		// 因为当前全部为默认可见，所以需要对可见性进行设置
		if (loadingView != null) {
			// 当前状态为Loading或者未知的时候显示加载界面
			loadingView
					.setVisibility(currentState == PageStateCode.STATE_LOADING
							|| currentState == PageStateCode.STATE_UNKOWN ? View.VISIBLE
							: View.INVISIBLE);
		}
		if (errorView != null) {
			errorView
					.setVisibility(currentState == PageStateCode.STATE_ERROR ? View.VISIBLE
							: View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView
					.setVisibility(currentState == PageStateCode.STATE_EMPTY ? View.VISIBLE
							: View.INVISIBLE);
		}
		/*
		 * 如果当前状态为成功加载，则创建成功界面 因为成功界面是需要数据从服务器的，所以在这里创建，而不是在initView方法中。
		 */
		if (currentState == PageStateCode.STATE_SUCCESS) {
			successView = createSuccessView();
			if (successView != null) {
				this.addView(successView, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				successView.setVisibility(View.VISIBLE);
			}
		} else {
			// 如果当前不是成功的状态，但是成功状态的界面存在，将其隐藏
			if (successView != null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
	}

	/*
	 * Loading方法，用于访问服务器的方法
	 * 根据从服务器获得的数据，刷新界面
	 */
	public void changeViewByServiceState() {
		// 因为当滑动过来的时候，需要进行加载，所以这里需要将状态改变，并且改变界面
		currentState = PageStateCode.STATE_LOADING;
		showViewByState();
		// 开新的线程获取数据
		ThreadPoolManager.getTheadManager().getThreadPoolProxy()
				.execute(new Runnable() {
					@Override
					public void run() {
						final int stateFromService = getStateAndDataFromService();
						//防止应用已经被终止了然后后台还在跑导致的错误
						if (MyApplication.getContext() != null) {
							UiUtils.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// 当获取到数据后，改变界面显示
									currentState = stateFromService;
									UiUtils.runOnUiThread(new Runnable() {
										@Override
										public void run() {
											showViewByState();
										}
									});
								}
							});
						}
					}
				});
	}

	/* 返回加载界面 */
	private View createLoadingView() {
		View loadingView = View.inflate(MyApplication.getContext(),
				R.layout.loadpage_loading, null);
		return loadingView;
	}

	/* 返回加载失败界面 */
	private View createErrorView() {
		final View errorView = View.inflate(MyApplication.getContext(),
				R.layout.loadpage_error, null);
		// 获取到刷新按钮，并为其添加单击监听
		Button refreshBtn = (Button) errorView.findViewById(R.id.page_bt);
		refreshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 改变状态为加载中
				currentState = PageStateCode.STATE_LOADING;
				// 隐藏错误界面
				errorView.setVisibility(View.INVISIBLE);
				showViewByState();
				changeViewByServiceState();
			}
		});
		return errorView;
	}

	/* 返回加载为空界面 */
	private View createEmptyView() {
		View emptyView = View.inflate(MyApplication.getContext(),
				R.layout.loadpage_error, null);
		return emptyView;
	}

	/*
	 * 创建加载成功的界面
	 * 因每个界面的成功界面不同，所以将方法抽象，因为不可能让每个类都能调用，所以使用protected修饰
	 */
	protected abstract View createSuccessView();

	/*
	 * 从服务器获取数据和状态
	 * 此方法运行在子线程中
	 * 将获取数据的方法进行抽象出来，因为每个界面获取数据的路径等不一样
	 */
	protected abstract int getStateAndDataFromService();
}
