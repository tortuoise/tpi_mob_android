package com.biz.stratadigm.tpi.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.biz.stratadigm.tpi.ui.fragment.LoginFragment;
import com.biz.stratadigm.tpi.ui.fragment.USerFragment;


/**
 * Created by tamara on 12/15/16.
 * Shows fragment in StartActivity
 */
public class UserPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    LoginFragment tab1;
    USerFragment tab2;

    public UserPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                tab1 = new LoginFragment();
                return tab1;

            case 1:
                tab2 = new USerFragment();
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
