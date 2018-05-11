package com.example.alirodhi.broiler;

import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.alirodhi.broiler.adapter.ViewPageAdapter;
import com.example.alirodhi.broiler.fragment.HomeFragment;
import com.example.alirodhi.broiler.fragment.LogFragment;
import com.example.alirodhi.broiler.fragment.RemoteFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
//import com.example.alirodhi.broiler.tab.MyAdapter;
//import com.example.alirodhi.broiler.tab.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {

    //deklarasi
    TabLayout MyTabs;
    ViewPager MyPage;
    RecyclerView mRecyclerView;

    private BottomBar bottomBar;
    private Fragment fragment = null;

    private TabLayout.Tab home;
    private TabLayout.Tab action;
    private TabLayout.Tab log;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFrament();

        bottomBar = (BottomBar)findViewById(R.id.bottombar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if(tabId == R.id.tab_home){
                    fragment = new HomeFragment();
                } else if(tabId == R.id.tab_remote){
                    fragment = new RemoteFragment();
                } else if(tabId == R.id.tab_log){
                    fragment = new LogFragment();
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.MyPage, fragment);
                fragmentTransaction.commit();
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_home){
                    fragment = new HomeFragment();
                } else if(tabId == R.id.tab_remote){
                    fragment = new RemoteFragment();
                } else if(tabId == R.id.tab_log){
                    fragment = new LogFragment();
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.MyPage, fragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void showFrament(){
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MyPage, fragment);
        fragmentTransaction.commit();
    }
}
