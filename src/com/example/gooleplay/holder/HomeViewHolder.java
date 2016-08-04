package com.example.gooleplay.holder;

import java.io.File;

import android.content.Context;
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
import com.example.gooleplay.bussiness.DownloadManager.Observer;
import com.example.gooleplay.utils.FileUtils;

/**
 * 首页的应用item的Holder
 * 
 * @author admin
 *
 */
public class HomeViewHolder extends BaseHolder<HomeDataBean> implements
		Observer {
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
				// 响应单击事件
				manager.actionState(datas.getPackageName());
			}
		});

		setText(R.id.txt_desc, datas.getDes());
		setText(R.id.txt_app_name, datas.getName());
		setText(R.id.txt_size,
				Formatter.formatFileSize(mContext, (long) datas.getSize()));

		// 发生了单击事件，说明需要改变注册Observable
		manager.registeObserver(observer, datas.getPackageName());
		File f = new File(FileUtils.getDownloadFile() + datas.getPackageName() + ".apk");
		if(f.exists() && f.length() == datas.getSize()) {
			manager.setState(datas.getPackageName(), manager.DONE_STATE);
		}
		f = null;

	}

	@Override
	public void onStateChange(int currentState) {
		Log.d(DEBUG_TAG, "接收到被观察者发过来的通知 ： onStateChange  " + currentState);
		switch (currentState) {
		case DownloadManager.DONE_STATE:
			tvState.setText("安装");
			btnDownload.setBackgroundResource(R.drawable.ic_install);
			break;
		case DownloadManager.DOWNLOADING_STATE:
			btnDownload.setBackgroundResource(R.drawable.ic_pause);
			break;
		case DownloadManager.IDLE_STATE:
			tvState.setText("下载");
			btnDownload.setBackgroundResource(R.drawable.ic_download);
			break;
		case DownloadManager.PAUSE_STATE:
			tvState.setText("暂停");
			btnDownload.setBackgroundResource(R.drawable.ic_resume);
			break;
		}
	}

	@Override
	public void onProgressChange(double currProgress, double total) {
		Log.d(DEBUG_TAG, "接收到被观察者发过来的通知 ： onProgressChange   " + currProgress
				+ "/" + total);
		double cur = currProgress / total * 100;
		String result = String.format("%.1f", cur);
		tvState.setText(result + "%");
	}

}
