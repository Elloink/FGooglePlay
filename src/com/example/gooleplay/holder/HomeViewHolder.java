package com.example.gooleplay.holder;

import android.R.string;
import android.content.Context;
import android.os.Debug;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.HomeDataBean;
import com.example.gooleplay.bussiness.DownloadManager;
import com.example.gooleplay.bussiness.Observable;
import com.example.gooleplay.bussiness.Observable.Observer;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.lidroid.xutils.db.annotation.Check;

/**
 * 首页的应用item的Holder
 * @author admin
 *
 */
public class HomeViewHolder extends BaseHolder<HomeDataBean> implements Observer{
	private String DEBUG_TAG = "HomeViewHolder";
	private DownloadManager manager = DownloadManager.getInstance();
	private Observer observer = this;
	private TextView tvState;
	private ImageView btnDownload;

	public HomeViewHolder(View itemView, Context context) {
		super(itemView, context);
	}

	@Override
	public void bindViewWithData(final HomeDataBean datas) {
		
		setImage(R.id.iv_icon, datas.getIconUrl());
		
		RatingBar ratingBar = (RatingBar) getView(R.id.ratingBar);
		ratingBar.setRating(datas.getStars());
		
		tvState = (TextView) getView(R.id.tv_state);
		
		btnDownload = (ImageView) getView(R.id.btn_download);
		btnDownload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//判断是否有Observable
				if(!manager.check(datas.getPackageName())) {
					manager.createObservable(datas.getDownloadUrl(), datas.getPackageName());
					manager.registeObserver(observer, datas.getPackageName());
				}
				manager.actionState(datas.getPackageName());
			}

			private void changeDrawable(int state) {
				
			}
		});
		
		setText(R.id.txt_desc, datas.getDes());
		setText(R.id.txt_app_name, datas.getName());
		setText(R.id.txt_size,
				Formatter.formatFileSize(mContext, (long) datas.getSize()));
		
		//判断是否有Observable
		if(!manager.check(datas.getPackageName())) {
			manager.createObservable(datas.getDownloadUrl(), datas.getPackageName());
			manager.registeObserver(observer, datas.getPackageName());
		}
	}

	@Override
	public void onStateChange(int currentState) {
		Log.d(DEBUG_TAG, "接收到被观察者发过来的通知 ： onStateChange  " + currentState);
		switch (currentState) {
		case Observable.DONE_STATE:
			tvState.setText("安装");
			btnDownload.setBackgroundResource(R.drawable.ic_install);
			break;
		case Observable.DOWNLOADING_STATE:
			btnDownload.setBackgroundResource(R.drawable.ic_downloading);
			break;
		case Observable.IDLE_STATE:
			tvState.setText("下载");
			btnDownload.setBackgroundResource(R.drawable.ic_download);
			break;
		case Observable.PAUSE_STATE:
			tvState.setText("暂停");
			btnDownload.setBackgroundResource(R.drawable.ic_pause);
			break;

		}
	}

	@Override
	public void onProgressChange(double currProgress, double total ) {
		Log.d(DEBUG_TAG, "接收到被观察者发过来的通知 ： onProgressChange   " + currProgress + "/" + total);
		double cur = currProgress / total * 100;
		String result = String.format("%.1f", cur);
		tvState.setText(result + "%");
	}

	
}
