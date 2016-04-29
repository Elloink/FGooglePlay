package com.example.gooleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gooleplay.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LeftMenuFragment extends Fragment implements OnClickListener {
	private ListView lvLeftMenu; // 侧边栏的ListView
	@ViewInject(R.id.iv_user_icon)
	private ImageView ivUserIcon; // 用户头像
	@ViewInject(R.id.txt_user_name)
	private TextView txtUserName; // 用户姓名
	@ViewInject(R.id.txt_user_email)
	private TextView txtUserEmail; // 用户邮箱
//	@ViewInject(R.id.rl_user_head)
	private RelativeLayout rlUserHead;	//登录部分的根目录

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_left_menu, container , false );
		initUI(view, container);
		handleEvent();	//处理View的事件（包括添加单击监听，设置适配器）
		return view;
	}
	

	/**
	 * 处理组件的事件
	 */
	private void handleEvent() {
		//添加单击事件
		rlUserHead.setOnClickListener(this);
		//添加Adapter
		lvLeftMenu.setAdapter(new ListViewAdapter());
	}

	private void initUI(View view, ViewGroup container) {
		lvLeftMenu = (ListView) view.findViewById(R.id.lv_left_menu);
		//两种写法皆可
//		View headView = LayoutInflater.from(getActivity()).inflate(
//				R.layout.item_head_left_menu , null );
		//如果为true的话，会出现的问题是已经有parent了，因为如果返回true就会调用addview方法
		View headView = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_head_left_menu , lvLeftMenu ,false);
		ViewUtils.inject(getActivity(), headView);
		rlUserHead = (RelativeLayout) headView.findViewById(R.id.rl_user_head);
		lvLeftMenu.addHeaderView(headView);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.rl_user_head:
			//当单击登录的时候
			Toast.makeText(getActivity(), "登录", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
	/*ListView的适配器*/
	class ListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getActivity(), 199, null);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			TextView tv = new TextView(getActivity());
			tv.setText("this is test data");
			return tv;
		}
		
		class ViewHolder {
			TextView txtItem;
			ImageView ivItemIcon;
		}
		
	}
}
