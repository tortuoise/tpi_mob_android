package com.biz.stratadigm.tpi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.ui.adapter.JobPagerAdapter;


/**
 * Starting activity.
 * Activity shows 4 fragment on for venue and thali
 * Use client current location and send it on server
 */
public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;

    private TabLayout mTabLayout;
    private JobPagerAdapter mPagerAdapter;
    private ViewPager mViewPagerJob;
    private Button mBtnOut;

    public static double longitude;
    public static double latitude;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationManager locationManager;
        locationManager = (LocationManager) getSystemService
                (Context.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation
                (LocationManager.PASSIVE_PROVIDER);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        } else {

        }

        // Get user location and check for permission
        // Set up tab layout
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("List of venue"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Add venue"));
        mTabLayout.addTab(mTabLayout.newTab().setText("List of thali"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Add thali"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Add photo"));

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mPagerAdapter = new JobPagerAdapter(getSupportFragmentManager(), 5);

        mViewPagerJob = (ViewPager) findViewById(R.id.pager);
        // mViewPagerJob.setPageTransformer(true, new StackTransformer());
        mViewPagerJob.setAdapter(mPagerAdapter);

        mBtnOut = (Button) findViewById(R.id.btnout);


        addListeners();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void addListeners() {
        mBtnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
                finish();
            }
        });

        mViewPagerJob.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
                mViewPagerJob.setCurrentItem(tab.getPosition());

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
