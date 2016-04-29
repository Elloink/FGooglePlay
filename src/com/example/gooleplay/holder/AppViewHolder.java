package com.example.gooleplay.holder;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;

public class AppViewHolder extends BaseHolder<AppDataBean> {

	public AppViewHolder(View itemView, Context context) {
		super(itemView, context);
	}

	@Override
	public void bindViewWithData(AppDataBean datas) {
		setImage(R.id.iv_icon, datas.getIconUrl());

		RatingBar ratingBar = (RatingBar) getView(R.id.ratingBar);
		ratingBar.setRating(datas.getStarts());

		ImageView btnDownload = (ImageView) getView(R.id.btn_download);

		setText(R.id.txt_desc, datas.getDes());
		setText(R.id.txt_app_name, datas.getName());
		setText(R.id.txt_size,
				Formatter.formatFileSize(mContext, (long) datas.getSize()));

	}

}
