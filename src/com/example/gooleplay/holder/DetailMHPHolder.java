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
 * 用于详情页面顶部部分的Holder
 * 
 * @author admin
 */

public class DetailMHPHolder extends BaseHolder<AppDataBean> {
	private final String DEBUG_TAG = "DetailMHPHolder";
	private boolean isStretchState = true; // View是否为伸张状态
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
		// 需要判断有多少个应用safe描述,并创建相应数量的view进container中
		judgeNumAndCrateItem(data);
		// 给图标添加单击事件，并改变图标的显示
		handleOnClick();
	}

	/*
	 * 处理单击事件
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
	 * 判断safe数组的大小，并且创建对应的item加入到container中
	 * 
	 * @param data
	 *            数据
	 */
	private void judgeNumAndCrateItem(AppDataBean data) {
		// 获取到两个容器
		LinearLayout appStateImgContainer = (LinearLayout) getView(R.id.ll_app_state_img_container);
		LinearLayout appStateTxtContainer = (LinearLayout) getView(R.id.ll_app_state_txt_container);

		safeCount = data.getSafeInfos().size(); // 获得safe的数量
		ArrayList<SafeInfo> safeInfos = data.getSafeInfos();

		// 填充stateImg容器
		fillStateImgContainer(safeInfos, appStateImgContainer);
		// 填充stateTxt容器
		fillStateTxtContainer(safeInfos, appStateTxtContainer);
	}

	/**
	 * 填充stateTxt容器
	 * 
	 * @param safeInfos
	 * @param container
	 */
	private void fillStateTxtContainer(ArrayList<SafeInfo> safeInfos,
			LinearLayout container) {
		// 循环构建view
		for (int count = 0; count < safeCount; count++) {
			// 创建一个新的View
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
			// 绑定数据
			BitmapUtils bitmapUtils = BitmapUtilsHelper.getInstance();
			bitmapUtils.display(ivSafeDes,
					HttpUrlUtils.getImageUrl(safeInfos.get(count).safeDesUrl));

			txtSafeDes.setText(safeInfos.get(count).safeDes);

			// 根据服务器数据显示不同的颜色
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
	 * 填充stateImg容器
	 * 
	 * @param safeInfos
	 * @param container
	 */
	private void fillStateImgContainer(ArrayList<SafeInfo> safeInfos,
			LinearLayout container) {
		// 循环构建view
		for (int count = 0; count < safeCount; count++) {
			
			ImageView safeIcon = new ImageView(mContext);
			safeIcon.setScaleType(ScaleType.FIT_XY);
			
			LinearLayout.LayoutParams layoutParams = new LayoutParams(
					UiUtils.dp2px(50), UiUtils.dp2px(25));
			layoutParams.leftMargin = UiUtils.dp2px(10);
			safeIcon.setLayoutParams(layoutParams);
			
			// 绑定数据
			BitmapUtils bitmapUtils = BitmapUtilsHelper.getInstance();
			bitmapUtils.display(safeIcon,
					HttpUrlUtils.getImageUrl(safeInfos.get(count).safeUrl));
			
			container.addView(safeIcon);
		}
	}

	/*
	 * 根据标志位，改变容器的显示状态 如果
	 */
	private void changeListState() {
		//动画的操作对象
		final LinearLayout llAppStateTxtContainer = (LinearLayout) getView(R.id.ll_app_state_txt_container);
		final android.view.ViewGroup.LayoutParams llAppStateTxtContainerLayoutParams = llAppStateTxtContainer
				.getLayoutParams();
		
		if (isFirstComeIn) {
			//第一次进入，记录高度
			mTxtContainerHeight = llAppStateTxtContainer.getMeasuredHeight();
			isFirstComeIn = false;
		}
		
		//动画处理部分
		ValueAnimator animator = null;
		if (isStretchState) {
			// 当前为伸展状态则改变为收缩
			animator = ValueAnimator.ofInt(mTxtContainerHeight, 0);
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					llAppStateTxtContainerLayoutParams.height = (Integer) animation.getAnimatedValue();
					llAppStateTxtContainer.setLayoutParams(llAppStateTxtContainerLayoutParams);
					//改变图标朝向
					if (animation.getAnimatedFraction() == 0) {
						ivArrow.setImageResource(R.drawable.arrow_down);
					}
				}
			});
		} else {
			// 当前为收缩的状态，改变为伸展
			animator = ValueAnimator.ofInt(0, mTxtContainerHeight);
			animator.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					llAppStateTxtContainerLayoutParams.height = (Integer) animation.getAnimatedValue();
					llAppStateTxtContainer.setLayoutParams(llAppStateTxtContainerLayoutParams);
					//改变图标朝向
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
