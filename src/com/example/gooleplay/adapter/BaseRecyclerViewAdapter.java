package com.example.gooleplay.adapter;

import java.util.List;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.gooleplay.R;
import com.example.gooleplay.holder.BaseHolder;
import com.example.gooleplay.holder.LoadingHolder;
import com.example.gooleplay.protocol.BaseProtocol;
import com.example.gooleplay.utils.ThreadPoolManager;
import com.example.gooleplay.utils.UiUtils;

/**
 * recylerView的适配器基类
 * @author admin
 *
 * @param <T> 数据集，请使用list的子类
 * @param <E> 单个数据，同时也是ViewHolder所使用的泛型
 */
public abstract class BaseRecyclerViewAdapter<T, E> extends
		RecyclerView.Adapter<BaseHolder<E>> {
	protected List<E> mDatas;
	protected Context mContext;
	private OnItemClickListener mListener;
	public static final int NORMAL_ITEM = 1;
	public static final int LOADING_ITEM = 2;
	public static final int VIEWPAGER = 0;

	public BaseRecyclerViewAdapter(List<E> datas, Context context) {
		this.mDatas = datas;
		this.mContext = context;
	}

	// *************设置点击事件
	/* 定义单击事件接口 */
	public interface OnItemClickListener {
		public void onItemClick(View v, int position);
	}

	/**
	 * 设置单击事件
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		if (listener != null) {
			mListener = listener;
		}
	}

	// *******************************

	@Override
	public int getItemCount() {
		//这里子类自己返回item的数量
		//主要是考虑到有的会有header或者是尾巴，数量可能会在数据集的基础上加
		return itemCount();
	}

	/**
	 * 子类实现，返回其数据集的大小 用于生成itemCount
	 * （考虑到数据集的类型和大小都有很大的变化，如果有特殊的item还不完全按照数据集大小来计算item数量，所以还是交给子类自己根据情况实现）
	 * 
	 * @return 数据集大小
	 */
	protected abstract int itemCount();

	/**
	 * 处理View，绑定数据，添加单击事件等等 这里交给viewholder自己去处理
	 */
	@Override
	public void onBindViewHolder(BaseHolder<E> holder, final int position) {
		// 交给ViewHolder自己去处理自己特有的数据
		holder.bindViewWithData(getItem(position));
		// 设置单击事件
		holder.itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemClick(v, position);
				}
			}
		});
	}
	
	/**
	 * 获得当前位置的单个数据
	 * @return
	 */
	protected abstract E getItem(int position);
	
	/**
	 * 返回当前的item的类型，判断是loading界面还是正常的item
	 */
	@Override
	public int getItemViewType(int position) {
		return position == (getItemCount() - 1) ? LOADING_ITEM : NORMAL_ITEM ;
	}

	/**
	 * 创建viewholder
	 * 因为加载界面是一样的，所以说当时normal的item的时候交给子类自己实现，如果是loading的item就直接完成逻辑
	 */
	 @Override
	 public BaseHolder onCreateViewHolder(ViewGroup root, int viewType) {
			// 分辨当前的holder是什么holder
			switch (viewType) {
			case NORMAL_ITEM : {
				return createNormalItem(root , viewType);
			}
			case  VIEWPAGER : {
				return createNormalItem(root , viewType);
			}
			case LOADING_ITEM: {
				View loadingView = LayoutInflater.from(mContext).inflate(
						R.layout.item_load_more, root, false);
				LoadingHolder loadingHolder = new LoadingHolder(loadingView,
						mContext, this);
				return loadingHolder;
			}
			}
			return null;
	 }
	
	
	 /**
	  * 创建正常的Item
	  * 交给子类自己实现
	 * @param viewType 
	 * @param root 
	  * @return 返回正常的item的holder
	  */
	protected abstract BaseHolder createNormalItem(ViewGroup root, int viewType) ;
	

	/**
	 * 加载更多的数据
	 * @param loadingHolder 
	 * @return 返回加载的结果，具体根据LoadResult类中的标识判断
	 */
	public void loadMoreData(final LoadingHolder loadingHolder) {
		ThreadPoolManager.getTheadManager().getThreadPoolProxy()
		.execute(new Runnable() {

			@Override
			public void run() {
				int page = (getItemCount() / 20 );
				BaseProtocol<T> protocol = getProtocol();
				final List moreData = (List)protocol.load(page);
				SystemClock.sleep(1500);
				UiUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (moreData == null) {
							loadingHolder
									.setLoadingState(LoadingHolder.LOADING_ERROR);
						} else {
							if (moreData.size() == 0) {
								loadingHolder
										.setLoadingState(LoadingHolder.NO_MORE);
							} else {
								mDatas.addAll(moreData);
								loadingHolder.mAdapter.notifyDataSetChanged();
								loadingHolder
										.setLoadingState(LoadingHolder.HAVA_DATA);
							}
						}
					}
				});
			}
		});

		
	}
	
	/**
	 * 获得当前adapter适用的协议类
	 */
	protected abstract BaseProtocol<T> getProtocol();

}
