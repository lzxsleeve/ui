package com.sleeve.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ViewPager 适配器
 * <p>
 * Create by lzx on 2019/9/23
 */
public class VPAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<TabVPTitleBean> mTitles;

    public VPAdapter(FragmentManager fm, List<Fragment> fragments, List<TabVPTitleBean> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    public VPAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null || mTitles.size() == 0) {
            return super.getPageTitle(position);
        } else {
            return mTitles.get(position).getTitle();
        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
