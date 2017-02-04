package biz.stratadigm.tpi.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import biz.stratadigm.tpi.ui.fragment.AddPhotoFragment;
import biz.stratadigm.tpi.ui.fragment.ThaliFragment;
import biz.stratadigm.tpi.ui.fragment.ThaliListFragment;
import biz.stratadigm.tpi.ui.fragment.VenueFragment;
import biz.stratadigm.tpi.ui.fragment.VenuesFragment;

/**
 * Created by tamara on 12/11/16.
 * Shows fragment in MainActivity
 */
public class JobPagerAdapter extends FragmentStatePagerAdapter {
    
    private static final String TAG = "TPI";
    private static int PAGE_NUMBER = 5;
    private static final int VENUELIST_TAB_POSITION = 0;
    private static final int VENUE_TAB_POSITION = 1;
    private static final int THALILIST_TAB_POSITION = 2;
    private static final int THALI_TAB_POSITION = 3;
    private static final int ADDPHOTO_TAB_POSITION = 4;

    public JobPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.PAGE_NUMBER = NumOfTabs;
        Log.v(TAG, "JobPagerAdapter");
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case VENUELIST_TAB_POSITION:
                return new VenuesFragment();
            case VENUE_TAB_POSITION:
                return  new VenueFragment();
            case THALILIST_TAB_POSITION:
                return new ThaliListFragment();
            case THALI_TAB_POSITION:
                return new ThaliFragment();
            case ADDPHOTO_TAB_POSITION:
                return new AddPhotoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

}
