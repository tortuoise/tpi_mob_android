package biz.stratadigm.tpi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.ui.adapter.UserPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Starting activity.
 * Activity shows two fragment on for reqistraton second for login
 */

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager jobViewPager;

    private UserPagerAdapter userPagerAdapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);

        //seting up tab layout and view pager
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Registration"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager());

        jobViewPager = (ViewPager) findViewById(R.id.pager);
        jobViewPager.setAdapter(userPagerAdapter);

        setListeners();

    }

    private void setListeners() {
        jobViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                userPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
                jobViewPager.setCurrentItem(tab.getPosition());
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
                userPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
            }
        });
    }
}
