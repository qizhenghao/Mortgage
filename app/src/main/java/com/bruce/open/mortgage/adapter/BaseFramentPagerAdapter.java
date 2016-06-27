package com.bruce.open.mortgage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.bruce.open.mortgage.fragments.BaseFragment;

import java.util.List;

public class BaseFramentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;


    public BaseFramentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        if (fragmentList != null) {
            return fragmentList.size();
        }
        return 0;
    }

    public void refresh(Class fragmentClass) {
        for (BaseFragment fragment : fragmentList) {
            if (fragment.getClass().getName().equals(fragmentClass.getName())) {
                fragment.refresh();
            }
        }
    }

}