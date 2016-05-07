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
 * recylerView������������
 * @author admin
 *
 * @param <T> ���ݼ�����ʹ��list������
 * @param <E> �������ݣ�ͬʱҲ��ViewHolder��ʹ�õķ���
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

	// *************���õ���¼�
	/* ���嵥���¼��ӿ� */
	public interface OnItemClickListener {
		public void onItemClick(View v, int position);
	}

	/**
	 * ���õ����¼�
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		if (listener != null) {
			mListener = listener;
		}
	}

	// *******************************

	@Override
	public int getItemCount() {
		//���������Լ�����item������
		//��Ҫ�ǿ��ǵ��еĻ���header������β�ͣ��������ܻ������ݼ��Ļ����ϼ�
		return itemCount();
	}

	/**
	 * ����ʵ�֣����������ݼ��Ĵ�С ��������itemCount
	 * �����ǵ����ݼ������ͺʹ�С���кܴ�ı仯������������item������ȫ�������ݼ���С������item���������Ի��ǽ��������Լ��������ʵ�֣�
	 * 
	 * @return ���ݼ���С
	 */
	protected abstract int itemCount();

	/**
	 * ����View�������ݣ���ӵ����¼��ȵ� ���ｻ��viewholder�Լ�ȥ����
	 */
	@Override
	public void onBindViewHolder(BaseHolder<E> holder, final int position) {
		// ����ViewHolder�Լ�ȥ�����Լ����е�����
		holder.bindViewWithData(getItem(position));
		// ���õ����¼�
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
	 * ��õ�ǰλ�õĵ�������
	 * @return
	 */
	protected abstract E getItem(int position);
	
	/**
	 * ���ص�ǰ��item�����ͣ��ж���loading���滹��������item
	 */
	@Override
	public int getItemViewType(int position) {
		return position == (getItemCount() - 1) ? LOADING_ITEM : NORMAL_ITEM ;
	}

	/**
	 * ����viewholder
	 * ��Ϊ���ؽ�����һ���ģ�����˵��ʱnormal��item��ʱ�򽻸������Լ�ʵ�֣������loading��item��ֱ������߼�
	 */
	 @Override
	 public BaseHolder onCreateViewHolder(ViewGroup root, int viewType) {
			// �ֱ浱ǰ��holder��ʲôholder
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
	  * ����������Item
	  * ���������Լ�ʵ��
	 * @param viewType 
	 * @param root 
	  * @return ����������item��holder
	  */
	protected abstract BaseHolder createNormalItem(ViewGroup root, int viewType) ;
	

	/**
	 * ���ظ��������
	 * @param loadingHolder 
	 * @return ���ؼ��صĽ�����������LoadResult���еı�ʶ�ж�
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
	 * ��õ�ǰadapter���õ�Э����
	 */
	protected abstract BaseProtocol<T> getProtocol();

}
