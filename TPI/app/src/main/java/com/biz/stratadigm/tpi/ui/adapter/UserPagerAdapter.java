package com.biz.stratadigm.tpi.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.biz.stratadigm.tpi.ui.fragment.LoginFragment;
import com.biz.stratadigm.tpi.ui.fragment.UserFragment;


/**
 * Created by tamara on 12/15/16.
 * Shows fragment in StartActivity
 */
public class UserPagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_NUMBER = 2;
    private static final int LOGIN_TAB_POSITION = 0;
    private static final int REGISTER_TAB_POSITION = 1;

    public UserPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case LOGIN_TAB_POSITION:
                return new LoginFragment();
            case REGISTER_TAB_POSITION:
                return new UserFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

}
