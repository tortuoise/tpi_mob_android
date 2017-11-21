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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.PopupMenu;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.App;
import biz.stratadigm.tpi.ui.adapter.JobPagerAdapter;
import biz.stratadigm.tpi.ui.view.MenuView;
import biz.stratadigm.tpi.ui.fragment.VenuesFragment;
import biz.stratadigm.tpi.ui.fragment.ThaliListFragment;
import biz.stratadigm.tpi.presenter.MenuPresenter;
import biz.stratadigm.tpi.util.ActivityUtils;
import biz.stratadigm.tpi.provider.FallbackLocationTracker;
import biz.stratadigm.tpi.provider.ProviderLocationTracker;
import biz.stratadigm.tpi.provider.ProviderLocationTracker.ProviderType;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseActivity extends AppCompatActivity implements VenuesFragment.OnVenueSelectedListener {

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private static final String TAG = "TPI";

    //@BindView(R.id.tab_layout)
    //TabLayout tabLayout;

    //@BindView(R.id.pager)
    //ViewPager jobViewPager;

    //@BindView(R.id.btnout)
    //Button btnOut;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    //@Inject LocationManager locationManager;

    private DrawerLayout mDrawerLayout;

    private JobPagerAdapter mPagerAdapter;

    public static double longitude;
    public static double latitude;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BrowseActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        ButterKnife.bind(this);

        ((App)getApplication()).getAppComponent().inject(this); // locationManager should now be available

        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setDisplayShowHomeEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        // Get user location and check for permission
        try {
        /*LocationManager locationManager;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Location location = locationManager.getLastKnownLocation
                        (LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                } else {

                }
        */
        FallbackLocationTracker locationTracker = new FallbackLocationTracker(this, ProviderType.GPS);
        locationTracker.start();
        Location location;
        if (locationTracker.hasLocation()) {
            location = locationTracker.getLocation();
        }
        else { //if (locationTracker.hasPossiblyStaleLocation())
            location = locationTracker.getPossiblyStaleLocation();
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        } catch (Exception e) {
                Log.v(TAG, "Location Manager " + e.toString());
        }
        
        // Add fragment to activity
        VenuesFragment venuesFragment = (VenuesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (venuesFragment == null) {
                venuesFragment = new VenuesFragment();
                try {
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), venuesFragment, R.id.contentFrame);
                } catch (Exception e) {
                        Log.v(TAG,  "addFragmentToActivity " + e.toString());
                }
        }
        // Set up tab layout
        /*tabLayout.addTab(tabLayout.newTab().setText("Venues"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Venue"));
        tabLayout.addTab(tabLayout.newTab().setText("Thalis"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Thali"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Image"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mPagerAdapter = new JobPagerAdapter(getSupportFragmentManager(), 5);

        //jobViewPager = (ViewPager) findViewById(R.id.pager);
        // jobViewPager.setPageTransformer(true, new StackTransformer());
        jobViewPager.setAdapter(mPagerAdapter);
        */
        addListeners();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                    Toast.makeText(this, "Found location ok!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_LONG).show();
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

        /*jobViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
        });*/
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.fragment_menu_main, menu);
        //return true;
    //}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
             case android.R.id.home:
                 // Open the navigation drawer when the home icon is selected from the toolbar.
                 mDrawerLayout.openDrawer(GravityCompat.START);
                 return true;
         }
         return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.statistics_navigation_menu_item:
                                //Intent intent =
                                //        new Intent(TasksActivity.this, StatisticsActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                //        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void onVenueSelect(long position, String name) {
        //ThaliListFragment thaliListFragment = (ThaliListFragment) getSupportFragmentManager().findFragmentById(R..fragment_thali_list);
        //if (thaliListFragment != null) {

        //} else {
            ThaliListFragment thaliListFragment = ThaliListFragment.newInstance();
            Bundle args = new Bundle();
            args.putLong(ThaliListFragment.ARG_POSITION, position);
            args.putString(ThaliListFragment.ARG_NAME, name);
            thaliListFragment.setArguments(args);
            ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), thaliListFragment, R.id.contentFrame);
        //}

    }

    public void onVenueLongSelect(int position, String name) {
          
        Toast.makeText(this, "Long click", Toast.LENGTH_LONG).show();

    }
    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
}
