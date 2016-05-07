package com.example.gooleplay.holder;

import java.util.Date;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.RatingBar;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.utils.BitmapUtilsHelper;

/**
 * 用于详情页面顶部部分的Holder
 * 
 * @author admin
 */

public class DetailHeadPHolder extends BaseHolder<AppDataBean> {

	public DetailHeadPHolder(View itemView, Context context) {
		super(itemView, context);
	}

	@Override
	public void bindViewWithData(AppDataBean data) {

		setImage(R.id.iv_app_icon, data.getIconUrl());
		setText(R.id.txt_app_name, data.getName());
		RatingBar rb = (RatingBar) getView(R.id.rb_app_stars);
		rb.setRating(data.getStarts());
		setText(R.id.txt_down_num, "下载量："+data.getDownloadNum());
		setText(R.id.txt_version, "版本号："+data.getVersion());
		setText(R.id.txt_date, "时间："+data.getDate());
		setText(R.id.txt_size,"大小："+
				Formatter.formatFileSize(mContext, (long) data.getSize()));
	}

}
