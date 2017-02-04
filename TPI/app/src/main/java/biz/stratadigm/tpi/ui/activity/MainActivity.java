package biz.stratadigm.tpi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.ui.adapter.JobPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Starting activity.
 * Activity shows 4 fragment on for venue and thali
 * Use client current location and send it on server
 */
public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final String TAG = "TPI";

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager jobViewPager;

    @BindView(R.id.btnout)
    Button btnOut;

    private JobPagerAdapter mPagerAdapter;

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
        ButterKnife.bind(this);

        Log.v(TAG, "MainActivity:onCreate");

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
        tabLayout.addTab(tabLayout.newTab().setText("List of venue"));
        tabLayout.addTab(tabLayout.newTab().setText("Add venue"));
        tabLayout.addTab(tabLayout.newTab().setText("List of thali"));
        tabLayout.addTab(tabLayout.newTab().setText("Add thali"));
        tabLayout.addTab(tabLayout.newTab().setText("Add photo"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mPagerAdapter = new JobPagerAdapter(getSupportFragmentManager(), 5);

        jobViewPager = (ViewPager) findViewById(R.id.pager);
        // jobViewPager.setPageTransformer(true, new StackTransformer());
        jobViewPager.setAdapter(mPagerAdapter);

        addListeners();
        Log.v(TAG, "MainActivity:onCreate Listeners added");

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
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
                finish();
            }
        });

        jobViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
                jobViewPager.setCurrentItem(tab.getPosition());

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
