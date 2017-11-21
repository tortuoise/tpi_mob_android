package biz.stratadigm.tpi.ui.activity;

import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import biz.stratadigm.tpi.ui.fragment.ThaliFragment;
import biz.stratadigm.tpi.ui.fragment.ThaliListFragment;
import biz.stratadigm.tpi.presenter.MenuPresenter;
import biz.stratadigm.tpi.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditThaliActivity extends AppCompatActivity {

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    public static final int REQUEST_ADD_THALI = 1;
    private static final String TAG = "TPI";

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    public static double longitude;
    public static double latitude;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, AddEditThaliActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        //((App)getApplication()).getAppComponent().inject(this); // locationManager should now be available

        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Add fragment to activity
        try {
        ThaliFragment thaliFragment = (ThaliFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        String thaliId = getIntent().getStringExtra(ThaliFragment.ARGUMENT_EDIT_THALI_ID);
        if (thaliFragment == null) {
                thaliFragment = new ThaliFragment();
                if (getIntent().hasExtra(ThaliFragment.ARGUMENT_EDIT_THALI_ID)) {
                    ab.setTitle(R.string.edit_thali);
                    Bundle bundle = new Bundle();
                    bundle.putString(ThaliFragment.ARGUMENT_EDIT_THALI_ID, thaliId);
                    thaliFragment.setArguments(bundle);
                } else {
                    ab.setTitle(R.string.new_thali);
                }
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), thaliFragment, R.id.contentFrame);
        }
        } catch (Exception e) {
                Log.v(TAG,  "addFragmentToActivity " + e.toString());
        }
        
        addListeners();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
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

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    //}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
