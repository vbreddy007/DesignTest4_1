package com.applicationtest.vbr.designtest4.com.vbr.teamselection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.applicationtest.vbr.designtest4.R;

import java.util.ArrayList;
import java.util.List;
import com.*;
import com.applicationtest.vbr.designtest4.com.vbr.dashboardclasses.Dashboard;

/**
 * Created by C5245675 on 4/15/2017.
 */
public class TeamSelection extends AppCompatActivity implements View.OnLongClickListener{
    boolean is_in_action_mode = false;

    ViewFlipper viewSwitcher;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomViewPager customViewPager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.team_selection_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView tv= (TextView)findViewById(R.id.my_contests);

        tv.setOnClickListener(new View.OnClickListener()

        {


            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(i);
            }
        }
        );


        customViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        customViewPager.setPagingEnabled(false);
        setupViewPager(customViewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(customViewPager);



        createTabIcons();


    }
    private void createTabIcons()
    {

        final TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custome_tab, null);
        tabOne.setText("Tab 1");
        tabOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabOne.setAllCaps(true);
            }
        });
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_launcher, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);


        final TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custome_tab, null);
        tabTwo.setText("Tab 2");
        tabTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabTwo.setAllCaps(true);
            }
        });

        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_launcher, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);



    }
    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
       // adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");

        viewPager.setAdapter(adapter);


    }

    @Override
    public boolean onLongClick(View view) {


        is_in_action_mode = true;





        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
