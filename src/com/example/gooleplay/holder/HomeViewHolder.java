package com.example.gooleplay.holder;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.HomeDataBean;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;

/**
 * 首页的应用item的Holder
 * @author admin
 *
 */
public class HomeViewHolder extends BaseHolder<HomeDataBean> {

	public HomeViewHolder(View itemView, Context context) {
		super(itemView, context);
	}

	@Override
	public void bindViewWithData(HomeDataBean datas) {
		
		setImage(R.id.iv_icon, datas.getIconUrl());
		
		RatingBar ratingBar = (RatingBar) getView(R.id.ratingBar);
		ratingBar.setRating(datas.getStars());
		
		ImageView btnDownload = (ImageView) getView(R.id.btn_download);
		
		setText(R.id.txt_desc, datas.getDes());
		setText(R.id.txt_app_name, datas.getName());
		setText(R.id.txt_size,
				Formatter.formatFileSize(mContext, (long) datas.getSize()));
	}

	
}
