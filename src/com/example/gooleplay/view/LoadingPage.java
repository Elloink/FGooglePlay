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
 * ���ڼ��ؽ����FrameLayout����Ҫ���ڼ�BaseFragment�Ĵ���
 * 
 * @author admin
 *
 */
public abstract class LoadingPage extends FrameLayout {

	// ��ǰ״̬
	private int currentState = PageStateCode.STATE_UNKOWN;

	// ��ʾ����״̬�Ĳ���
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
	 * ���г�ʼ���ĺ���
	 * һ�����Ƚ����ؽ��棬������棬�ս���Ĳ��ּ��ص�Fragment�з��ţ���Ϊ�����������Ǿ�̬�����Կ�����ǰ������
	 * Ȼ���ٸ���״̬���ı�������ʾ
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
		// ����״̬��ʾ����
		showViewByState();
	}

	/* 
	 * ����״̬��ʾҳ�� 
	 */
	private void showViewByState() {
		// ��Ϊ��ǰȫ��ΪĬ�Ͽɼ���������Ҫ�Կɼ��Խ�������
		if (loadingView != null) {
			// ��ǰ״̬ΪLoading����δ֪��ʱ����ʾ���ؽ���
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
		 * �����ǰ״̬Ϊ�ɹ����أ��򴴽��ɹ����� ��Ϊ�ɹ���������Ҫ���ݴӷ������ģ����������ﴴ������������initView�����С�
		 */
		if (currentState == PageStateCode.STATE_SUCCESS) {
			successView = createSuccessView();
			if (successView != null) {
				this.addView(successView, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				successView.setVisibility(View.VISIBLE);
			}
		} else {
			// �����ǰ���ǳɹ���״̬�����ǳɹ�״̬�Ľ�����ڣ���������
			if (successView != null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
	}

	/*
	 * Loading���������ڷ��ʷ������ķ���
	 * ���ݴӷ�������õ����ݣ�ˢ�½���
	 */
	public void changeViewByServiceState() {
		// ��Ϊ������������ʱ����Ҫ���м��أ�����������Ҫ��״̬�ı䣬���Ҹı����
		currentState = PageStateCode.STATE_LOADING;
		showViewByState();
		// ���µ��̻߳�ȡ����
		ThreadPoolManager.getTheadManager().getThreadPoolProxy()
				.execute(new Runnable() {
					@Override
					public void run() {
						final int stateFromService = getStateAndDataFromService();
						//��ֹӦ���Ѿ�����ֹ��Ȼ���̨�����ܵ��µĴ���
						if (MyApplication.getContext() != null) {
							UiUtils.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// ����ȡ�����ݺ󣬸ı������ʾ
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

	/* ���ؼ��ؽ��� */
	private View createLoadingView() {
		View loadingView = View.inflate(MyApplication.getContext(),
				R.layout.loadpage_loading, null);
		return loadingView;
	}

	/* ���ؼ���ʧ�ܽ��� */
	private View createErrorView() {
		final View errorView = View.inflate(MyApplication.getContext(),
				R.layout.loadpage_error, null);
		// ��ȡ��ˢ�°�ť����Ϊ����ӵ�������
		Button refreshBtn = (Button) errorView.findViewById(R.id.page_bt);
		refreshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �ı�״̬Ϊ������
				currentState = PageStateCode.STATE_LOADING;
				// ���ش������
				errorView.setVisibility(View.INVISIBLE);
				showViewByState();
				changeViewByServiceState();
			}
		});
		return errorView;
	}

	/* ���ؼ���Ϊ�ս��� */
	private View createEmptyView() {
		View emptyView = View.inflate(MyApplication.getContext(),
				R.layout.loadpage_error, null);
		return emptyView;
	}

	/*
	 * �������سɹ��Ľ���
	 * ��ÿ������ĳɹ����治ͬ�����Խ�����������Ϊ��������ÿ���඼�ܵ��ã�����ʹ��protected����
	 */
	protected abstract View createSuccessView();

	/*
	 * �ӷ�������ȡ���ݺ�״̬
	 * �˷������������߳���
	 * ����ȡ���ݵķ������г����������Ϊÿ�������ȡ���ݵ�·���Ȳ�һ��
	 */
	protected abstract int getStateAndDataFromService();
}
