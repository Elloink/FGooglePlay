package com.example.gooleplay.fragment;

import java.util.ArrayList;

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
	// @ViewInject(R.id.rl_user_head)
	private RelativeLayout rlUserHead; // ��¼���ֵĸ�Ŀ¼
	
	//���ͼ������ֵ����ݼ���
	ArrayList<ItemDataBean> datas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_left_menu, container, false);
		initData();

		initUI(view, container);
		handleEvent(); // ����View���¼���������ӵ���������������������
		return view;
	}

	private void initData() {
		//Ϊ�˱�֤����Ͷ�Ӧ
		datas = new ArrayList<LeftMenuFragment.ItemDataBean>();
		datas.add(new ItemDataBean("��ҳ", R.drawable.ic_home));
		datas.add(new ItemDataBean("����", R.drawable.ic_setting));
		datas.add(new ItemDataBean("����", R.drawable.ic_theme));
		datas.add(new ItemDataBean("��װ������", R.drawable.ic_scans));
		datas.add(new ItemDataBean("����", R.drawable.ic_feedback));
		datas.add(new ItemDataBean("������", R.drawable.ic_updates));
		datas.add(new ItemDataBean("����", R.drawable.ic_about));
		datas.add(new ItemDataBean("�˳�", R.drawable.ic_exit));
	}

	/**
	 * ����������¼�
	 */
	private void handleEvent() {
		// ��ӵ����¼�
		rlUserHead.setOnClickListener(this);
		// ���Adapter
		lvLeftMenu.setAdapter(new ListViewAdapter(datas));
	}

	private void initUI(View view, ViewGroup container) {
		lvLeftMenu = (ListView) view.findViewById(R.id.lv_left_menu);
		// ����д���Կ�
		// View headView = LayoutInflater.from(getActivity()).inflate(
		// R.layout.item_head_left_menu , null );
		// ���Ϊtrue�Ļ�������ֵ��������Ѿ���parent�ˣ���Ϊ�������true�ͻ����addview����
		View headView = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_head_left_menu, lvLeftMenu, false);
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
			// ��������¼��ʱ��
			Toast.makeText(getActivity(), "��¼", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	/* ListView�������� */
	class ListViewAdapter extends BaseAdapter {
		ArrayList<ItemDataBean> datas;
		
		public ListViewAdapter(ArrayList<ItemDataBean> datas) {
			this.datas = datas;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public ItemDataBean getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getActivity(),
						R.layout.item_left_menu_item, null);
				holder.txtItem = (TextView) convertView.findViewById(R.id.txt_item);
				holder.ivItemIcon = (ImageView) convertView.findViewById(R.id.iv_item_icon);
				convertView.setTag(holder);
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ItemDataBean item = getItem(position);
			holder.txtItem.setText(item.title);
			holder.ivItemIcon.setImageResource(item.imageId);
			return convertView;
		}

		class ViewHolder {
			TextView txtItem;
			ImageView ivItemIcon;
		}

	}
	
	class ItemDataBean {
		public ItemDataBean(String title , int id) {
			this.title = title;
			this.imageId = id;
		}
		public String title;
		public int imageId;
	}
}
