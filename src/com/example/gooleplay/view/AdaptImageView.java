package com.example.gooleplay.view;

import android.content.Context;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


/**
 * 自适应ImageView
 * 根据长宽按照一定的比例进行换算
 * 注：在这里使用的时候因为是宽度确定（match_parent）所以这里按照2.41的比例进行换算
 *    宽度优先于高度
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
		
//		Log.d(DEBUG_TAG, "测量前距离为" + heightSize);
		if(widthMode == MeasureSpec.EXACTLY) {
			widthSize = widthSize - getPaddingLeft() - getPaddingRight();
			heightSize = (int)(widthSize / scale);
//			Log.d(DEBUG_TAG, "测量后按比例换算的距离为" + heightSize);
		} else if(heightMode == MeasureSpec.EXACTLY) {
			heightSize = heightSize - getPaddingBottom() - getPaddingTop();
			widthSize = (int)(heightSize * scale);
		}
		
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, widthMode);
		
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
