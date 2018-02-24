package com.example.alirodhi.broiler;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alirodhi.broiler.adapter.RecyclerAdapter;
import com.example.alirodhi.broiler.adapter.ViewPageAdapter;
import com.example.alirodhi.broiler.fragment.HomeFragment;
import com.example.alirodhi.broiler.fragment.LogFragment;
import com.example.alirodhi.broiler.fragment.StatusFragment;
//import com.example.alirodhi.broiler.tab.MyAdapter;
//import com.example.alirodhi.broiler.tab.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //deklarasi
    TabLayout MyTabs;
    ViewPager MyPage;
    RecyclerView mRecyclerView;

    private TabLayout.Tab home;
    private TabLayout.Tab action;
    private TabLayout.Tab log;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //memanggil id
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        MyTabs = (TabLayout)findViewById(R.id.MyTabs);
        MyPage = (ViewPager)findViewById(R.id.MyPage);

        //Toolbar
        setSupportActionBar(toolbar);

        //Tablayout dan viewpage
        MyTabs.setupWithViewPager(MyPage);
        SetUpViewPager(MyPage);

        //Menambahkan icon pada tab layout
        MyTabs.getTabAt(0).setIcon(R.drawable.slc_home);
        MyTabs.getTabAt(1).setIcon(R.drawable.slc_remote);
        MyTabs.getTabAt(2).setIcon(R.drawable.slc_history);

    }

    //untuk view page
    public void SetUpViewPager (ViewPager MyPage){
        ViewPageAdapter Adapter = new ViewPageAdapter(getSupportFragmentManager());

        Adapter.AddFragmentPage(new HomeFragment(), "Home");
        Adapter.AddFragmentPage(new StatusFragment(), "Remote");
        Adapter.AddFragmentPage(new LogFragment(), "Log");

        MyPage.setAdapter(Adapter);
    }
}
