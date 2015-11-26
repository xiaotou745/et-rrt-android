package com.task.service;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import base.BaseFragment;
/**
 * viewpage 与fragment结合的适配器
 * @author EricHu
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	private Context context;
	List<BaseFragment> list;

	public MyFragmentPagerAdapter(FragmentManager fm, Context context,
			List<BaseFragment> list) {
		super(fm);
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public Fragment instantiateItem(ViewGroup container, int position) {
		Fragment fragment = (Fragment) super.instantiateItem(container,
				position);
		((FragmentActivity) context).getSupportFragmentManager()
				.beginTransaction().show(fragment).commit();
		return fragment;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// super.destroyItem(container, position, object);
		Fragment fragment = list.get(position);
		((FragmentActivity) context).getSupportFragmentManager()
				.beginTransaction().hide(fragment).commit();
	}

}
