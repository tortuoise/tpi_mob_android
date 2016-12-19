package com.biz.stratadigm.tpi.activity;

import android.Manifest;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.biz.stratadigm.tpi.adapters.JobPagerAdapter;
import com.biz.stratadigm.tpi.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Starting activity.
 * Activity shows 4 fragment on for venue and thali
 * Use client current location and send it on server
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;

    private TabLayout mTabLayout;
    private JobPagerAdapter mPagerAdapter;
    public static ViewPager mViewPagerJob;

    private LocationManager lm;
    public static Location location;
    public static double longitude;
    public static double latitude;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get user location and check for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();


        lm = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        // Set up tab layout
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Add venue"));
        mTabLayout.addTab(mTabLayout.newTab().setText("List of venue"));
        mTabLayout.addTab(mTabLayout.newTab().setText("List of thali"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Add thali"));

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mPagerAdapter = new JobPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPagerJob = (ViewPager) findViewById(R.id.pager);
        // mViewPagerJob.setPageTransformer(true, new StackTransformer());
        mViewPagerJob.setAdapter(mPagerAdapter);


        addListeners();


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();// connect google location api
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();// disconnect google location api
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {// Get location

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();


        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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
                if (tab.getPosition() == 2) {
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
