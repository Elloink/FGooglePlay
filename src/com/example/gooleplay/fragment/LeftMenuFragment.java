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
	private ListView lvLeftMenu; // �������ListView
	@ViewInject(R.id.iv_user_icon)
	private ImageView ivUserIcon; // �û�ͷ��
	@ViewInject(R.id.txt_user_name)
	private TextView txtUserName; // �û�����
	@ViewInject(R.id.txt_user_email)
	private TextView txtUserEmail; // �û�����
//	@ViewInject(R.id.rl_user_head)
	private RelativeLayout rlUserHead;	//��¼���ֵĸ�Ŀ¼

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_left_menu, container , false );
		initUI(view, container);
		handleEvent();	//����View���¼���������ӵ���������������������
		return view;
	}
	

	/**
	 * ����������¼�
	 */
	private void handleEvent() {
		//��ӵ����¼�
		rlUserHead.setOnClickListener(this);
		//���Adapter
		lvLeftMenu.setAdapter(new ListViewAdapter());
	}

	private void initUI(View view, ViewGroup container) {
		lvLeftMenu = (ListView) view.findViewById(R.id.lv_left_menu);
		//����д���Կ�
//		View headView = LayoutInflater.from(getActivity()).inflate(
//				R.layout.item_head_left_menu , null );
		//���Ϊtrue�Ļ�������ֵ��������Ѿ���parent�ˣ���Ϊ�������true�ͻ����addview����
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
			//��������¼��ʱ��
			Toast.makeText(getActivity(), "��¼", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
	/*ListView��������*/
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
