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
 * ��ҳ����ViewPager��Holder��
 * ����������ViewPager
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
	 * ��Ϊ�����������Ϊnull�ģ��������ﲻʹ�ô�������ݣ������ڴ�����ʱ��ͽ����ݳ�ʼ��
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
		// ��ֹͣ��ǰδִ����ϵ�ʱ��
		handler.removeCallbacks(autoScrollRunnable);
		if (isAutoScroll) {
			// ȷ�Ͽ�ʼ�Զ�����
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
			// �ж��Ƿ���
			if (contentViews.size() > 0) {
				iv = contentViews.remove(0);
			} else {
				iv = new ImageView(mContext);
			}
			// װ��ͼƬ��ImageView
			BitmapUtilsHelper.getInstance().display(iv,
					HttpUrlUtils.getImageUrl(datas[position % datas.length]));
			// ��ӵ����¼�
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("��ǰ����Viewpager����������Ŀ�ǣ� "
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
	 * ��������
	 * 
	 * @param data
	 */
	public void setDatas(String[] data) {
		this.datas = data;
	}

	/**
	 * ���ݵ����¼���ֹͣ�������Զ�����
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
