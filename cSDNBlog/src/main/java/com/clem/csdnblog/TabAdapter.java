package com.clem.csdnblog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
	
	public static final String[] TITLES = new String[]{
		"移动开发", "Web前端", "架构设计", "编程语言", "互联网", "数据库", "系统运维", "云计算", "研发管理", "综合"
	};

	public TabAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public Fragment getItem(int arg0) {
		MainFragment fragment = new MainFragment(arg0 + 1);
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TITLES.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return TITLES[position % TITLES.length];
	}
	
	

}
