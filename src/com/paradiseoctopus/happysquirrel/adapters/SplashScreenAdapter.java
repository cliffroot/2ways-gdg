package com.paradiseoctopus.happysquirrel.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.paradiseoctopus.happysquirrel.fragments.SplashFragment_;

public class SplashScreenAdapter extends FragmentPagerAdapter{

	public final int NUMBER_OF_PAGES = 3;

	ViewPager pg;

	public SplashScreenAdapter(FragmentManager fm, ViewPager pg) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		return SplashFragment_.newInstance(index);
	}

	@Override
	public int getCount() {
		return NUMBER_OF_PAGES;
	}

}