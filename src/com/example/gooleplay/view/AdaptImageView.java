package com.example.gooleplay.view;

import android.content.Context;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


/**
 * ����ӦImageView
 * ���ݳ�����һ���ı������л���
 * ע��������ʹ�õ�ʱ����Ϊ�ǿ��ȷ����match_parent���������ﰴ��2.41�ı������л���
 *    ��������ڸ߶�
 * @author admin
 *
 */
public class AdaptImageView extends ImageView {
	private String DEBUG_TAG = "AdaptImageView";
	
	private float scale = 2.41f;

	public AdaptImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public AdaptImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AdaptImageView(Context context) {
		super(context);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
//		Log.d(DEBUG_TAG, "����ǰ����Ϊ" + heightSize);
		if(widthMode == MeasureSpec.EXACTLY) {
			widthSize = widthSize - getPaddingLeft() - getPaddingRight();
			heightSize = (int)(widthSize / scale);
//			Log.d(DEBUG_TAG, "�����󰴱�������ľ���Ϊ" + heightSize);
		} else if(heightMode == MeasureSpec.EXACTLY) {
			heightSize = heightSize - getPaddingBottom() - getPaddingTop();
			widthSize = (int)(heightSize * scale);
		}
		
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, widthMode);
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
