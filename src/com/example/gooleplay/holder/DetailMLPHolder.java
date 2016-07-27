package com.example.gooleplay.holder;

import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.example.gooleplay.R;
import com.example.gooleplay.bean.AppDataBean;
import com.example.gooleplay.utils.BitmapUtilsHelper;
import com.example.gooleplay.utils.HttpUrlUtils;

public class DetailMLPHolder extends BaseHolder<AppDataBean> {

	public DetailMLPHolder(View itemView, Context context) {
		super(itemView, context);
		View view = View.inflate(mContext, R.layout.item_detail_mid_low, null);
		ivs=new ImageView[5];
		ivs[0]=(ImageView) view.findViewById(R.id.screen_1);
		ivs[1]=(ImageView) view.findViewById(R.id.screen_2);
		ivs[2]=(ImageView) view.findViewById(R.id.screen_3);
		ivs[3]=(ImageView) view.findViewById(R.id.screen_4);
		ivs[4]=(ImageView) view.findViewById(R.id.screen_5);
		
		((HorizontalScrollView)itemView).addView(view);
	}

	private ImageView[] ivs;
	@Override
	public void bindViewWithData(AppDataBean data) {
		String[] screen = data.getScreen();
		for(int count = 0; count < 5; count++) {
			if(count<screen.length){
				ivs[count].setVisibility(View.VISIBLE);
				BitmapUtilsHelper.getInstance().display(ivs[count], HttpUrlUtils.getImageUrl(screen[count]));
			}else{
				ivs[count].setVisibility(View.GONE);
			}
		}
	}
	

}
