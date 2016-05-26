package com.example.gooleplay.holder;

import android.content.Context;
import android.view.View;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.SubjectBean;

/**
 * 专题页面的item的Holder
 * @author admin
 *
 */
public class SubjectViewHolder extends BaseHolder<SubjectBean> {

	public SubjectViewHolder(View itemView, Context context) {
		super(itemView, context);
	}

	@Override
	public void bindViewWithData(SubjectBean data) {
		setText(R.id.txt_sub_des, data.getDes());
		setImage(R.id.iv_sub_pic, data.getUrl());
	}
}
