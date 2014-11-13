package com.example.firstapp;

import com.example.firstapp.fragment.InputFragment;
import com.example.firstapp.fragment.MessageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

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
		}
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

}
