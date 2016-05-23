package com.example.gooleplay.holder;

import java.util.ArrayList;

import android.R.integer;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.bean.AppDataBean.SafeInfo;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.example.gooleplay.utils.UiUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * ��������ҳ�涥�����ֵ�Holder
 * 
 * @author admin
 */

public class DetailMHPHolder extends BaseHolder<AppDataBean> {
	private final String DEBUG_TAG = "DetailMHPHolder";
	private boolean isStretchState = true; // View�Ƿ�Ϊ����״̬
	private boolean isFirstComeIn = true;
	private int safeCount;
	private ImageView ivArrow;
	private int mTxtContainerHeight = 0;

	public DetailMHPHolder(View itemView, Context context) {
		super(itemView, context);
		ivArrow = (ImageView) getView(R.id.iv_arrow);
	}

	@Override
	public void bindViewWithData(AppDataBean data) {
		// ��Ҫ�ж��ж��ٸ�Ӧ��safe����,��������Ӧ������view��container��
		judgeNumAndCrateItem(data);
		// ��ͼ����ӵ����¼������ı�ͼ�����ʾ
		handleOnClick();
	}

	/*
	 * �������¼�
	 */
	private void handleOnClick() {
		RelativeLayout rl_container = (RelativeLayout) getView(R.id.rl_app_state_img_container);
		rl_container.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeListState();
			}
		});
	}

	/**
	 * �ж�safe����Ĵ�С�����Ҵ�����Ӧ��item���뵽container��
	 * 
	 * @param data
	 *            ����
	 */
	private void judgeNumAndCrateItem(AppDataBean data) {
		// ��ȡ����������
		LinearLayout appStateImgContainer = (LinearLayout) getView(R.id.ll_app_state_img_container);
		LinearLayout appStateTxtContainer = (LinearLayout) getView(R.id.ll_app_state_txt_container);

		safeCount = data.getSafeInfos().size(); // ���safe������
		ArrayList<SafeInfo> safeInfos = data.getSafeInfos();

		// ���stateImg����
		fillStateImgContainer(safeInfos, appStateImgContainer);
		// ���stateTxt����
		fillStateTxtContainer(safeInfos, appStateTxtContainer);
	}

	/**
	 * ���stateTxt����
	 * 
	 * @param safeInfos
	 * @param container
	 */
	private void fillStateTxtContainer(ArrayList<SafeInfo> safeInfos,
			LinearLayout container) {
		// ѭ������view
		for (int count = 0; count < safeCount; count++) {
			// ����һ���µ�View
			View itemSafeDes = View.inflate(mContext, R.layout.item_safe_des,
					null);
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.leftMargin = UiUtils.dp2px(8);
			layoutParams.bottomMargin = UiUtils.dp2px(5);
			itemSafeDes.setLayoutParams(layoutParams);
			
			ImageView ivSafeDes = (ImageView) UiUtils.getView(itemSafeDes,
					R.id.iv_safe_des);
			TextView txtSafeDes = (TextView) itemSafeDes
					.findViewById(R.id.txt_safe_des);
			// ������
			BitmapUtils bitmapUtils = BitmapUtilsHelper.getInstance();
			bitmapUtils.display(ivSafeDes,
					HttpUrlUtils.getImageUrl(safeInfos.get(count).safeDesUrl));

			txtSafeDes.setText(safeInfos.get(count).safeDes);

			// ���ݷ�����������ʾ��ͬ����ɫ
			int color = getColor(safeInfos.get(count).safeDesColor);
			txtSafeDes.setTextColor(color);

			container.addView(itemSafeDes);
		}

	}
	
	private int getColor(int colorType) {
		int color;
		if (colorType >= 1 && colorType <= 3) {
			color = Color.rgb(255, 153, 0); // 00 00 00
		} else if (colorType == 4) {
			color = Color.rgb(0, 177, 62);
		} else {
			color = Color.rgb(122, 122, 122);
		}
		return color;
	}
	

	/**
	 * ���stateImg����
	 * 
	 * @param safeInfos
	 * @param container
	 */
	private void fillStateImgContainer(ArrayList<SafeInfo> safeInfos,
			LinearLayout container) {
		// ѭ������view
		for (int count = 0; count < safeCount; count++) {
			
			ImageView safeIcon = new ImageView(mContext);
			safeIcon.setScaleType(ScaleType.FIT_XY);
			
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					UiUtils.dp2px(50), UiUtils.dp2px(25));
			layoutParams.leftMargin = UiUtils.dp2px(10);
			safeIcon.setLayoutParams(layoutParams);
			
			// ������
			BitmapUtils bitmapUtils = BitmapUtilsHelper.getInstance();
			bitmapUtils.display(safeIcon,
					HttpUrlUtils.getImageUrl(safeInfos.get(count).safeUrl));
			
			container.addView(safeIcon);
		}
	}

	/*
	 * ���ݱ�־λ���ı���������ʾ״̬ ���
	 */
	private void changeListState() {
		//�����Ĳ�������
		final LinearLayout llAppStateTxtContainer = (LinearLayout) getView(R.id.ll_app_state_txt_container);
		final android.view.ViewGroup.LayoutParams llAppStateTxtContainerLayoutParams = llAppStateTxtContainer
				.getLayoutParams();
		
		if (isFirstComeIn) {
			//��һ�ν��룬��¼�߶�
			mTxtContainerHeight = llAppStateTxtContainer.getMeasuredHeight();
			isFirstComeIn = false;
		}
		
		//����������
		ValueAnimator animator = null;
		if (isStretchState) {
			// ��ǰΪ��չ״̬��ı�Ϊ����
			animator = ValueAnimator.ofInt(mTxtContainerHeight, 0);
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					llAppStateTxtContainerLayoutParams.height = (Integer) animation.getAnimatedValue();
					llAppStateTxtContainer.setLayoutParams(llAppStateTxtContainerLayoutParams);
					//�ı�ͼ�곯��
					if (animation.getAnimatedFraction() == 0) {
						ivArrow.setImageResource(R.drawable.arrow_down);
					}
				}
			});
		} else {
			// ��ǰΪ������״̬���ı�Ϊ��չ
			animator = ValueAnimator.ofInt(0, mTxtContainerHeight);
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					llAppStateTxtContainerLayoutParams.height = (Integer) animation.getAnimatedValue();
					llAppStateTxtContainer.setLayoutParams(llAppStateTxtContainerLayoutParams);
					//�ı�ͼ�곯��
					if (animation.getAnimatedFraction() == 1) {
						ivArrow.setImageResource(R.drawable.arrow_up);
					}
				}
			});
		}
		animator.setDuration(300);
		animator.start();
		
		isStretchState = !isStretchState;
	}

}
