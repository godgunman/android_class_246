package com.example.firstapp;

import com.example.firstapp.fragment.InputFragment;
import com.example.firstapp.fragment.MessageFragment;
import com.example.firstapp.fragment.PhotoFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	private final static String[] TITLE = {"Input", "Message", "Photo"};
	
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new InputFragment();
		case 1:
			return new MessageFragment();
		case 2:
			return new PhotoFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLE[position];
	}
}
