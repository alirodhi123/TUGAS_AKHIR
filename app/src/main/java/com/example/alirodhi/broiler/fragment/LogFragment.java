package com.example.alirodhi.broiler.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alirodhi.broiler.R;
//import com.example.alirodhi.broiler.Tab.SlidingTabLayout;
import com.example.alirodhi.broiler.adapter.AdapterSlidingTab;
import com.example.alirodhi.broiler.db.DatabaseHelper;

/**
 * Created by alirodhi on 7/5/2018.
 */

public class LogFragment extends Fragment {

    private DatabaseHelper db;

    Toolbar toolbar;
    ViewPager pager;
    AdapterSlidingTab adapter;
    TabLayout tabs;
    CharSequence Titles[]={"Sensor","Relay"};
    int Numboftabs =2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        db = new DatabaseHelper(getContext());

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        TextView textViewToolbar = (TextView)view.findViewById(R.id.toolbar_text);

        setSupportActionBar(toolbar);
        if(textViewToolbar!=null && toolbar!=null) {
            textViewToolbar.setText("Logs Activity");
            setSupportActionBar(toolbar);
        }

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new AdapterSlidingTab(getActivity().getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        // Assiging the Sliding Tab Layout View
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setupWithViewPager(pager);

        return view;
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}
