package com.example.gooleplay.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.HomeDataBean;
import com.example.gooleplay.holder.BaseHolder;
import com.example.gooleplay.holder.HomeViewHolder;
import com.example.gooleplay.holder.HomeViewPager;
import com.example.gooleplay.protocol.BaseProtocol;
import com.example.gooleplay.protocol.HomeProtocol;

/**
 * 首页的recyclerview的适配器
 * @author admin
 *
 */
public class HomeAdapter extends
		BaseRecyclerViewAdapter<ArrayList<HomeDataBean>, HomeDataBean> {
	
	private static String DEBUG_TAG = "HomeAdapter";
	

	public HomeAdapter(ArrayList<HomeDataBean> datas, Context context) {
		super(datas, context);
	}

	@Override
	protected int itemCount() {
		// 加1为loading的item
		return mDatas.size() + 1 + 1;
	}

	@Override
	protected HomeDataBean getItem(int position) {
		if (position == (mDatas.size() + 1) || position == 0) {
			return null;
		}
		//拿去数据的时候减去顶部的viewpager
		return mDatas.get(position - 1);
	}

	@Override
	protected BaseProtocol<ArrayList<HomeDataBean>> getProtocol() {
		return new HomeProtocol();
	}
	
	@Override
	public int getItemViewType(int position) {
		if(position == 0) {
			return VIEWPAGER;
		}
		return super.getItemViewType(position);
	}

	@Override
	protected BaseHolder createNormalItem(ViewGroup root, int viewType) {
		if(viewType == VIEWPAGER) {
			// 创建一个ViewPager
			View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_home_viewpager, root,false);
			HomeViewPager homeViewPager = new HomeViewPager(itemView, mContext);
			String[] picDatas = ((HomeProtocol)getProtocol()).getPicDatas();
			Log.d(DEBUG_TAG, "图片数据为 : " + picDatas.toString());
			homeViewPager.setDatas(picDatas);
			homeViewPager.setAutoScroll(true);
			return homeViewPager;
		}
		HomeViewHolder holder;
		View itemView = View.inflate(mContext, R.layout.item_home, null);
		holder = new HomeViewHolder(itemView, mContext);
		return holder;
	}
}
