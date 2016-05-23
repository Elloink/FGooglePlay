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
 * ����Ӧ������ҳ��ײ���holder ����չʾӦ�������������Ϣ
 * 
 * @author admin
 *
 */
public class DetailFooterHolder extends BaseHolder<AppDataBean> {

	private static final String DEBUG_TAG = "DetailFooterHolder";
	private TextView desContent;
	private TextView desAuthor;
	private ImageView desArrow;
	private boolean isStretchState = false; // View�Ƿ�Ϊ����״̬
	private RelativeLayout rlAuthor;

	public DetailFooterHolder(View itemView, Context context) {
		super(itemView, context);

		initUI();
	}


	@Override
	public void bindViewWithData(AppDataBean data) {
		//��ʼ��������
		initData(data);
		
		//����content���ݲ���Ĭ��Ϊ7�еĸ߶�
		LayoutParams layoutParams = desContent.getLayoutParams();
		layoutParams.height = get7LineHeight();
		desContent.setLayoutParams(layoutParams);
		
		//�������¼�
		rlAuthor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeLength();
			}
		});
	}

	/*
	 * ��ʼ������
	 */
	private void initData(AppDataBean data) {
		desContent.setText(data.getDes());
		desAuthor.setText(data.getAuthor());
	}
	
	/*
	 * ��ʼ�����
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
//		Log.d(DEBUG_TAG, "��������7������ĸ߶�Ϊ : "+ result);
		tv = null;
		layoutParams = null;
		return result;
	}

	
	private int getFullHeight() {
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(desContent.getWidth(), MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec((1 << 30) - 1, MeasureSpec.AT_MOST);
		desContent.measure(widthMeasureSpec, heightMeasureSpec);
//		Log.d(DEBUG_TAG, "��ȡ����ȫ���߶�Ϊ: " +desContent.getMeasuredHeight());
		return desContent.getMeasuredHeight();
	}
	
	private void changeLength() {
		ValueAnimator valueAnimator = null;
		if(isStretchState) {
			//��Ϊ����
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
			//��Ϊ��չ
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
