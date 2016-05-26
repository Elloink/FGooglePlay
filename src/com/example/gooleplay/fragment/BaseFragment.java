package com.example.gooleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.gooleplay.view.LoadingPage;

/**
 * Fragment基类
 * 
 * @author admin
 *
 */
public abstract class BaseFragment extends Fragment {

	// 用于最后显示的帧布局
	private LoadingPage mFrameLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mFrameLayout == null) {
			mFrameLayout = new LoadingPage(getActivity()) {
				
				@Override
				public int getStateAndDataFromService() {
					//调用子类所实现的方法
					return BaseFragment.this.getStateAndDataFromService();
				}
				
				@Override
				public View createSuccessView() {
					//调用子类所实现的方法
					return BaseFragment.this.createSuccessView();
				}
			};
		} else {
			// 去除之前的ParentView
			ViewParent parent = mFrameLayout.getParent();
			if (parent instanceof ViewGroup) {
				((ViewGroup) parent).removeView(mFrameLayout);
			}
			
			//因为所有的View都被清除了，所以需要重新加载一次
			mFrameLayout.init();
		}
		return mFrameLayout;
	}

	
	/*当摧毁的时候，将所有的View销毁，避免重新加载的时候有残留*/
	@Override
	public void onDestroy() {
		super.onDestroy();
		mFrameLayout.removeAllViews();
	}
	

	/* 返回加载成功界面
	 * 需要子类自己实现 
	 */
	protected abstract View createSuccessView();
	
	/* 从服务器获取数据，并返回状态 
	 * 需要子类自己实现
	 */
	protected abstract int getStateAndDataFromService();


	public void changeViewByServiceState() {
		mFrameLayout.changeViewByServiceState();
	}
}
