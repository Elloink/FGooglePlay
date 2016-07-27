package com.example.gooleplay.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gooleplay.gloable.MyApplication;

/**
 * ��ҳFragment
 * @author admin
 *
 */
public class GameFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected View createSuccessView() {
		TextView tv = new TextView(MyApplication.getContext());
		tv.setText("Game������سɹ�");
		return tv;
	}

	@Override
	protected int getStateAndDataFromService() {
		synchronized(this) {
			SystemClock.sleep(2000);
			return 2;
		}
	}
	 
	 
}
