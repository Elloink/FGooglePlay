package com.example.gooleplay.fragment;

import android.util.SparseArray;

public class FragmentFactory {

	private static SparseArray<BaseFragment> fragmentArray = new SparseArray<BaseFragment>();

	
	/**
	 * ��ȡ����Ӧposition��Fragment
	 * @param position Fragment�Ľű�
	 * @return
	 */
	public static BaseFragment getFragment(int position) {
		BaseFragment fragment = fragmentArray.get(position);
		if (fragment == null) {
			switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new AppFragment();
				break;
			case 2:
				fragment = new GameFragment();
				break;
			case 3:
				fragment = new SubjectFragment();
				break;
			case 4:
				fragment = new CategoryFragment();
				break;
			case 5:
				fragment = new TopFragment();
				break;

			default:
				throw new RuntimeException("��ǰpositionû�ж�Ӧ��Fragment");
			}
			fragmentArray.put(position,fragment);
		}
		
		return fragment;
	}
}
