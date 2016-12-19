package com.biz.stratadigm.tpi.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.biz.stratadigm.tpi.fragments.VenueFragment;
import com.biz.stratadigm.tpi.fragments.ThaliListFragment;
import com.biz.stratadigm.tpi.fragments.ThaliFragment;
import com.biz.stratadigm.tpi.fragments.VenueListFragment;

/**
 * Created by tamara on 12/11/16.
 * Shows fragment in MainActivity
 */
public class JobPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    VenueListFragment tab1;
    ThaliFragment tab2;
    VenueFragment tab3;
    ThaliListFragment tab4;

    public JobPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                tab3 = new VenueFragment();
                return tab3;

            case 1:
                tab1 = new VenueListFragment();
                return tab1;
            case 2:
                tab4 = new ThaliListFragment();
                return tab4;
            case 3:
                tab2 = new ThaliFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
