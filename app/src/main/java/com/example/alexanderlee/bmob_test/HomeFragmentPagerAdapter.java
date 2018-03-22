package com.example.alexanderlee.bmob_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderlee on 2017/10/27.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String>titleList;
    private ArrayList<Fragment>fragmentList;
    public HomeFragmentPagerAdapter(FragmentManager fm, ArrayList<String>titleList,ArrayList<Fragment>fragmentList) {
        super(fm);
        this.titleList=titleList;
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
