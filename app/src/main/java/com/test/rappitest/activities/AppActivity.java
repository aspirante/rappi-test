package com.test.rappitest.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.test.rappitest.R;
import com.test.rappitest.adapter.ViewPagerAdapter;
import com.test.rappitest.fragments.PopularFragment;
import com.test.rappitest.fragments.TopRatedFragment;
import com.test.rappitest.fragments.UpComingFragment;

public class AppActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayot_id);
        mViewPager = (ViewPager) findViewById(R.id.viewPager_id);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mTabLayout.setTabTextColors (Color.BLACK, Color.WHITE);

        //Add Fragment
        mAdapter.AddFragment(new PopularFragment(),"Popular");
        mAdapter.AddFragment(new TopRatedFragment(),"Top rated");
        mAdapter.AddFragment(new UpComingFragment(),"Up coming");

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

}
