package com.example.prakhar.movieapp.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.prakhar.movieapp.ui.home.home_movie.HomeMovieFragment;
import com.example.prakhar.movieapp.ui.home.home_people.HomePeopleFragment;
import com.example.prakhar.movieapp.ui.home.home_tv.HomeTvFragment;
import com.example.prakhar.movieapp.utils.SmartFragmentStatePagerAdapter;

/**
 * Created by Prakhar on 4/7/2017.
 */

public class HomeViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 3;
    private static final String tabTitles[] = new String[] {"Movie", "TV", "People"};

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeMovieFragment.newInstance();
            case 1:
                return HomeTvFragment.newInstance();
            case 2:
                return HomePeopleFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
