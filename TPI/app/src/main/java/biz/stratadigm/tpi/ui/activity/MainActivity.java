package biz.stratadigm.tpi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.support.v7.widget.PopupMenu;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.ui.adapter.JobPagerAdapter;
import biz.stratadigm.tpi.ui.view.MenuView;
import biz.stratadigm.tpi.presenter.MenuPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final String TAG = "TPI";

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager jobViewPager;

    //@BindView(R.id.btnout)
    //Button btnOut;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

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

        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Get user location and check for permission
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

        // Set up tab layout
        tabLayout.addTab(tabLayout.newTab().setText("Venues"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Venue"));
        tabLayout.addTab(tabLayout.newTab().setText("Thalis"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Thali"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Image"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mPagerAdapter = new JobPagerAdapter(getSupportFragmentManager(), 5);

        //jobViewPager = (ViewPager) findViewById(R.id.pager);
        // jobViewPager.setPageTransformer(true, new StackTransformer());
        jobViewPager.setAdapter(mPagerAdapter);

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
        /*btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
                finish();
            }
        });*/

        jobViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                mPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
                jobViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                        Log.v(TAG, "ONE");
                }
                if (tab.getPosition() == 2) {
                        Log.v(TAG, "TWO");
                }
                } catch (Exception e) {
                    Log.v(TAG, e.toString());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                } catch (Exception e) {
                    Log.v(TAG, e.toString());
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mPagerAdapter.getItem(tab.getPosition()).onAttach(getApplicationContext());
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_menu_main, menu);
        return true;
    }*/

}
