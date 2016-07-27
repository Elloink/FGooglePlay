package com.example.gooleplay.holder;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gooleplay.R;
import com.example.gooleplay.adapter.CategoryRecyclerAdapter;
import com.example.gooleplay.bean.CategoryDataBean.SingleCategoryData;
import com.example.gooleplay.bean.CategoryDataBean.SingleCategoryData.EachLineNameAndImg;
import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;
import com.lidroid.xutils.BitmapUtils;

public class CategroyViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
	private View mRootView;
	private int mViewType;
	private ArrayList<SingleCategoryData> mDatas;
	private int currentPos = -1;
	private int firstCatLines ;
	private RelativeLayout[] rls;

	public CategroyViewHolder(View itemView, int viewType) {
		super(itemView);
		this.mRootView = itemView;
		this.mViewType = viewType;
	}

	/**
	 * 当前View的位置，根据这个位置来判断其应该去哪个数据
	 * 
	 * @param position
	 * @param mDatas
	 */
	public void bindDataWithView(int position,
			ArrayList<SingleCategoryData> mDatas) {
		this.mDatas = mDatas;
		if(mDatas != null) {
			firstCatLines = mDatas.get(0).totalLinesNameAndImages.size();
		}
		currentPos = position;
		switch (mViewType) {
		case CategoryRecyclerAdapter.CATEGROY_ITEM:
			handleCategroyItem();
			break;
		case CategoryRecyclerAdapter.NORMAL_ITEM:
			handleNormalItem();
			break;

		}
	}

	private void handleNormalItem() {
		/*
		 * 获得当前位置的对应数据
		 */
		int dataPos = -1;
		int catPos = -1;
		if (currentPos <= firstCatLines) {
			catPos = 0;
			dataPos = currentPos - 1;
		} else {
			catPos = 1;
			dataPos = currentPos - 1 - 1 - firstCatLines;
		}

		/*
		 * 获得到当前位置的数据对象
		 */
		EachLineNameAndImg eachLineNameAndImg = mDatas.get(catPos).totalLinesNameAndImages
				.get(dataPos);

		//获得组件
		ImageView[] ivs = new ImageView[3];
		ivs[0] = (ImageView) mRootView.findViewById(R.id.iv_1);
		ivs[1] = (ImageView) mRootView.findViewById(R.id.iv_2);
		ivs[2] = (ImageView) mRootView.findViewById(R.id.iv_3);

		TextView[] tvs = new TextView[3];
		tvs[0] = (TextView) mRootView.findViewById(R.id.tv_1);
		tvs[1] = (TextView) mRootView.findViewById(R.id.tv_2);
		tvs[2] = (TextView) mRootView.findViewById(R.id.tv_3);
		
		rls = new RelativeLayout[3];
		rls[0] = (RelativeLayout) mRootView.findViewById(R.id.rl_1);
		rls[1] = (RelativeLayout) mRootView.findViewById(R.id.rl_2);
		rls[2] = (RelativeLayout) mRootView.findViewById(R.id.rl_3);
		for(int i = 0; i < 3; i++) {
			rls[i].setOnClickListener(this);
		}
		//绑定数据
		String[] images = eachLineNameAndImg.images;
		String[] names = eachLineNameAndImg.names;
		
		BitmapUtils bitmapUtils = BitmapUtilsHelper.getInstance();
		for(int i = 0; i < 3; i++) {
			if(images[i] == null || images[i].equals("")) {
				//如果为空表示没数据，所以直接跳过
				continue;
			}
			bitmapUtils.display(ivs[i], HttpUrlUtils.getImageUrl(images[i]));
			tvs[i].setText(names[i]);
		}
	}

	private void handleCategroyItem() {
		if (currentPos == 0) {
			((TextView) mRootView).setText(mDatas.get(0).title);
		} else {
			((TextView) mRootView).setText(mDatas.get(1).title);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_1:
				Toast.makeText(MyApplication.getContext(), ((TextView)v.findViewById(R.id.tv_1)).getText(), Toast.LENGTH_SHORT).show();
				break;
			case R.id.rl_2:
				Toast.makeText(MyApplication.getContext(), ((TextView)v.findViewById(R.id.tv_2)).getText(), Toast.LENGTH_SHORT).show();
				break;
			case R.id.rl_3:
				Toast.makeText(MyApplication.getContext(), ((TextView)v.findViewById(R.id.tv_3)).getText(), Toast.LENGTH_SHORT).show();
				break;
		}
	}

}
