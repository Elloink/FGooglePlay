package com.example.gooleplay.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.holder.AppViewHolder;
import com.example.gooleplay.holder.BaseHolder;
import com.example.gooleplay.holder.LoadingHolder;
import com.example.gooleplay.protocol.AppProtocol;
import com.example.gooleplay.protocol.BaseProtocol;

/**
 * Ӧ�ý����recyclerview��Adapter�����ڸ�Ӧ�ý����recyclerview�ṩ����
 * @author admin
 *
 */
public class AppRecyclerViewAdapter extends BaseRecyclerViewAdapter<List<AppDataBean>, AppDataBean> {

	public AppRecyclerViewAdapter(List<AppDataBean> datas, Context context) {
		super(datas, context);
	}

	@Override
	protected int itemCount() {
		return mDatas.size();
	}

	@Override
	protected AppDataBean getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	protected BaseProtocol getProtocol() {
		return new AppProtocol();
	}

	@Override
	protected BaseHolder createNormalItem(ViewGroup root, int viewType) {
		View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_home, root , false);
		AppViewHolder appViewHolder = new AppViewHolder(itemView, mContext);
		return appViewHolder;
	}

}
