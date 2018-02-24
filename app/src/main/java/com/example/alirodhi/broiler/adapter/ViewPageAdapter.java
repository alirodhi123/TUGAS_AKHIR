package com.example.alirodhi.broiler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alirodhi.broiler.fragment.HomeFragment;
import com.example.alirodhi.broiler.fragment.LogFragment;
import com.example.alirodhi.broiler.fragment.StatusFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alirodhi on 2/17/2018.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {
    public List<Fragment> MyFragment = new ArrayList<>();
    public List<String> MyPageTittle = new ArrayList<>();

    public ViewPageAdapter(FragmentManager manager) {
        super(manager);
    }

    public void AddFragmentPage(Fragment Frag, String Title) {
        MyFragment.add(Frag);
        MyPageTittle.add(Title);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) // tab 1
        {
            HomeFragment tab1 = new HomeFragment();
            return tab1;
        }
        else if(position == 1) // tab 2
        {
            StatusFragment tab2 = new StatusFragment();
            return tab2;
        }
        else // tab 3
        {
            LogFragment tab3 = new LogFragment();
            return tab3;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return MyPageTittle.get(position); //jika ingin menggunakan tulisan pada tab
        return null; //jika inging menghilangkan tulisan pada tab
    }

    @Override
    public int getCount() {
        return 3;
    }
}

