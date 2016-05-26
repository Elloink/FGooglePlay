package com.example.gooleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.gooleplay.view.LoadingPage;

/**
 * Fragment����
 * 
 * @author admin
 *
 */
public abstract class BaseFragment extends Fragment {

	// ���������ʾ��֡����
	private LoadingPage mFrameLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mFrameLayout == null) {
			mFrameLayout = new LoadingPage(getActivity()) {
				
				@Override
				public int getStateAndDataFromService() {
					//����������ʵ�ֵķ���
					return BaseFragment.this.getStateAndDataFromService();
				}
				
				@Override
				public View createSuccessView() {
					//����������ʵ�ֵķ���
					return BaseFragment.this.createSuccessView();
				}
			};
		} else {
			// ȥ��֮ǰ��ParentView
			ViewParent parent = mFrameLayout.getParent();
			if (parent instanceof ViewGroup) {
				((ViewGroup) parent).removeView(mFrameLayout);
			}
			
			//��Ϊ���е�View��������ˣ�������Ҫ���¼���һ��
			mFrameLayout.init();
		}
		return mFrameLayout;
	}

	
	/*���ݻٵ�ʱ�򣬽����е�View���٣��������¼��ص�ʱ���в���*/
	@Override
	public void onDestroy() {
		super.onDestroy();
		mFrameLayout.removeAllViews();
	}
	

	/* ���ؼ��سɹ�����
	 * ��Ҫ�����Լ�ʵ�� 
	 */
	protected abstract View createSuccessView();
	
	/* �ӷ�������ȡ���ݣ�������״̬ 
	 * ��Ҫ�����Լ�ʵ��
	 */
	protected abstract int getStateAndDataFromService();


	public void changeViewByServiceState() {
		mFrameLayout.changeViewByServiceState();
	}
}
