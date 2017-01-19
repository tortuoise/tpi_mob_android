package biz.stratadigm.tpi.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import biz.stratadigm.tpi.ui.fragment.AddPhotoFragment;
import biz.stratadigm.tpi.ui.fragment.ThaliFragment;
import biz.stratadigm.tpi.ui.fragment.ThaliListFragment;
import biz.stratadigm.tpi.ui.fragment.VenueFragment;
import biz.stratadigm.tpi.ui.fragment.VenueListFragment;

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
    AddPhotoFragment tab5;

    public JobPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                tab1 = new VenueListFragment();
                return tab1;
            case 1:
                tab3 = new VenueFragment();
                return tab3;

            case 2:
                tab4 = new ThaliListFragment();
                return tab4;
            case 3:
                tab2 = new ThaliFragment();
                return tab2;
            case 4:
                tab5 = new AddPhotoFragment();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
