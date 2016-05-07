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
 * ��������ҳ�涥�����ֵ�Holder
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
		setText(R.id.txt_down_num, "��������"+data.getDownloadNum());
		setText(R.id.txt_version, "�汾�ţ�"+data.getVersion());
		setText(R.id.txt_date, "ʱ�䣺"+data.getDate());
		setText(R.id.txt_size,"��С��"+
				Formatter.formatFileSize(mContext, (long) data.getSize()));
	}

}
