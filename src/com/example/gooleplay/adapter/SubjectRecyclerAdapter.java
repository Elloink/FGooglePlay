package com.example.gooleplay.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.SubjectBean;
import com.example.gooleplay.holder.BaseHolder;
import com.example.gooleplay.holder.LoadingHolder;
import com.example.gooleplay.holder.SubjectViewHolder;
import com.example.gooleplay.protocol.BaseProtocol;
import com.example.gooleplay.protocol.SubjectProtocol;

/**
 * ר��ҳ���������
 * @author admin
 *
 */
public class SubjectRecyclerAdapter extends
		BaseRecyclerViewAdapter<List<SubjectBean>, SubjectBean> {

	public SubjectRecyclerAdapter(List<SubjectBean> datas, Context context) {
		super(datas, context);
	}

	@Override
	protected int itemCount() {
		return mDatas.size();
	}

	@Override
	protected SubjectBean getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	protected BaseProtocol getProtocol() {
		return new SubjectProtocol();
	}

	@Override
	protected BaseHolder createNormalItem(ViewGroup root, int viewType) {
		View itemView = LayoutInflater.from(mContext).inflate(
				R.layout.item_subject, root, false);
		SubjectViewHolder viewHolder = new SubjectViewHolder(itemView, mContext);
		return viewHolder;
	}
}
