package com.biz.stratadigm.tpi.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.biz.stratadigm.tpi.adapters.UserPagerAdapter;
import com.biz.stratadigm.tpi.R;

/**
 * Starting activity.
 * Activity shows two fragment on for reqistraton second for login
 */

public class StartActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private UserPagerAdapter mPagerAdapter;
    public static ViewPager mViewPagerJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //seting up tab layout and view pager
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("LogIn"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Registration"));

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mPagerAdapter = new UserPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPagerJob = (ViewPager) findViewById(R.id.pager);
        mViewPagerJob.setAdapter(mPagerAdapter);

        addListeners();

    }
    /**
     * View liseteners
     */
    private void addListeners() {
        mViewPagerJob.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
                mViewPagerJob.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {

                }
                if (tab.getPosition() == 1) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
            }
        });
    }
}
