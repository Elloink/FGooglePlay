package com.example.gooleplay.holder;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.utils.UiUtils;

/**
 * 用于应用详情页面底部的holder 用于展示应用详情的文字信息
 * 
 * @author admin
 *
 */
public class DetailFooterHolder extends BaseHolder<AppDataBean> {

	private static final String DEBUG_TAG = "DetailFooterHolder";
	private TextView desContent;
	private TextView desAuthor;
	private ImageView desArrow;
	private boolean isStretchState = false; // View是否为伸张状态
	private RelativeLayout rlAuthor;

	public DetailFooterHolder(View itemView, Context context) {
		super(itemView, context);

		initUI();
	}


	@Override
	public void bindViewWithData(AppDataBean data) {
		//初始化绑定数据
		initData(data);
		
		//设置content内容部分默认为7行的高度
		LayoutParams layoutParams = desContent.getLayoutParams();
		layoutParams.height = get7LineHeight();
		desContent.setLayoutParams(layoutParams);
		
		//处理单击事件
		rlAuthor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeLength();
			}
		});
	}

	/*
	 * 初始化数据
	 */
	private void initData(AppDataBean data) {
		desContent.setText(data.getDes());
		desAuthor.setText(data.getAuthor());
	}
	
	/*
	 * 初始化组件
	 */
	private void initUI() {
		desContent = (TextView) getView(R.id.des_content);
		desAuthor = (TextView) getView(R.id.des_author);
		desArrow = (ImageView) getView(R.id.des_arrow);
		rlAuthor = (RelativeLayout) getView(R.id.rl_author);
	}

	private int get7LineHeight() {
		int result = 0;

		TextView tv = new TextView(mContext);
		ViewGroup.LayoutParams layoutParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(layoutParams);
		tv.setLines(7);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 14);
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(desContent.getWidth(), MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec((1 << 30) - 1, MeasureSpec.AT_MOST);
		tv.measure(widthMeasureSpec, heightMeasureSpec);
		result = tv.getMeasuredHeight();
//		Log.d(DEBUG_TAG, "测量到的7行字体的高度为 : "+ result);
		tv = null;
		layoutParams = null;
		return result;
	}

	
	private int getFullHeight() {
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(desContent.getWidth(), MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec((1 << 30) - 1, MeasureSpec.AT_MOST);
		desContent.measure(widthMeasureSpec, heightMeasureSpec);
//		Log.d(DEBUG_TAG, "获取到的全部高度为: " +desContent.getMeasuredHeight());
		return desContent.getMeasuredHeight();
	}
	
	private void changeLength() {
		ValueAnimator valueAnimator = null;
		if(isStretchState) {
			//改为收缩
			valueAnimator = ValueAnimator.ofInt(getFullHeight() , get7LineHeight());
			valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					LayoutParams layoutParams = desContent.getLayoutParams();
					layoutParams.height = (Integer) animation.getAnimatedValue();
					desContent.setLayoutParams(layoutParams);
					if(layoutParams.height == get7LineHeight()) {
						desArrow.setImageResource(R.drawable.arrow_down);
					}
				}
			});
		} else {
			//改为伸展
			valueAnimator = ValueAnimator.ofInt( get7LineHeight() , getFullHeight());
			valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					LayoutParams layoutParams = desContent.getLayoutParams();
					layoutParams.height = (Integer) animation.getAnimatedValue();
					desContent.setLayoutParams(layoutParams);
					if(layoutParams.height == getFullHeight()) {
						desArrow.setImageResource(R.drawable.arrow_up);
					}
				}
			});
		}
		valueAnimator.setDuration(400);
		valueAnimator.start();
		isStretchState = !isStretchState;
	}
}
