package com.example.gooleplay.holder;

import java.util.LinkedList;

import com.example.gooleplay.R;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.lidroid.xutils.BitmapUtils;

import android.R.layout;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

/**
 * 首页顶部ViewPager的Holder类
 * 创建顶部的ViewPager
 * @author admin
 *
 */
public class HomeViewPager extends BaseHolder<String[]> implements
		OnTouchListener{

	private String[] datas;
	private ViewPager vp;
	private LinkedList<ImageView> contentViews = new LinkedList<ImageView>();

	public HomeViewPager(View itemView, Context context) {
		super(itemView, context);
	}

	/**
	 * 因为传入的数据是为null的，所以这里不使用传入的数据，而是在创建的时候就将数据初始化
	 */
	@Override
	public void bindViewWithData(String[] data) {
		vp = (ViewPager) getView(R.id.vp_top);
		HomeViewPagerAdapter adapter = new HomeViewPagerAdapter();
		vp.setAdapter(adapter);
		vp.setCurrentItem(datas.length * 2000);
		vp.setOnTouchListener(this);
	}

	Handler handler = new Handler();
	Runnable autoScrollRunnable = new Runnable() {

		@Override
		public void run() {
			int current = vp.getCurrentItem() + 1;
			vp.setCurrentItem(current);
			handler.postDelayed(this, 2000);
		}
	};

	public void setAutoScroll(boolean isAutoScroll) {
		// 先停止当前未执行完毕的时间
		handler.removeCallbacks(autoScrollRunnable);
		if (isAutoScroll) {
			// 确认开始自动滚动
			handler.postDelayed(autoScrollRunnable, 2000);
		}
	}

	class HomeViewPagerAdapter extends PagerAdapter {
		ImageView contentView = null;

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			ImageView iv;
			// 判断是否复用
			if (contentViews.size() > 0) {
				iv = contentViews.remove(0);
			} else {
				iv = new ImageView(mContext);
			}
			// 装载图片进ImageView
			BitmapUtilsHelper.getInstance().display(iv,
					HttpUrlUtils.getImageUrl(datas[position % datas.length]));
			// 添加单击事件
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("当前顶部Viewpager被单击的项目是： "
							+ datas[position % datas.length]);
				}
			});
			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			contentViews.add((ImageView) object);
			container.removeView((ImageView) object);
		}

		@Override
		public int getCount() {
			// return datas.length;
			return Integer.MAX_VALUE / 2;
		}
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	public void setDatas(String[] data) {
		this.datas = data;
	}

	/**
	 * 根据单击事件来停止和启动自动滑动
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			setAutoScroll(false);
			break;
		case MotionEvent.ACTION_CANCEL:
			setAutoScroll(true);
			break;
		case MotionEvent.ACTION_UP:
			setAutoScroll(true);
			break;
		default:
			break;
		}
		return false;
	}
}
