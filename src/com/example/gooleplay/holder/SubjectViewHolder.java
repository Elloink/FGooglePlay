package com.example.gooleplay.holder;

import android.content.Context;
import android.database.DataSetObservable;
import android.view.View;
import android.widget.ImageView;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.SubjectBean;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;

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
