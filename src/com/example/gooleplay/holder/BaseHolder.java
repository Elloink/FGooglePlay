package com.example.gooleplay.holder;

import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseHolder<E> extends ViewHolder {
	// ��ǰitem��view
	public View itemView;
	// ����ά����ǰ�����е����
	private SparseArray<View> views;
	
	protected Context mContext;

	public BaseHolder(View itemView , Context context) {
		super(itemView);
		this.itemView = itemView;
		views = new SparseArray<View>();
		mContext = context;
	}
	
	public void changeItemView(View newItemView) {
		itemView = newItemView;
	}

	/**
	 * ��View��Data
	 */
	public abstract void bindViewWithData(E data);

	/**
	 * ����ID���View
	 * 
	 * @param viewID
	 *            view��id
	 * @return ����View
	 */
	public View getView(int viewID) {
		View view = views.get(viewID);
		if (view == null) {
			// ˵��û���õ�
			view = itemView.findViewById(viewID);
			views.put(viewID, view);
		}
		return view;
	}
	
	public void setText(int txtId , String text) {
		TextView view = (TextView) getView(txtId);
		view.setText(text);
	}
	
	public void setImage(int ivID, String iamgeUrl) {
		BitmapUtilsHelper.getInstance().display(getView(ivID), HttpUrlUtils.getImageUrl(iamgeUrl));
	}

}
