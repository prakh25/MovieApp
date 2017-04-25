package com.example.prakhar.mobile.ui.people_full_credit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.core.model.people_detail.Cast;
import com.example.core.model.people_detail.Crew;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prakhar on 4/6/2017.
 */

public class PeopleFullCreditPagerAdapter extends FragmentStatePagerAdapter {

    private final static int PAGE_COUNT = 2;

    private ArrayList<Cast> casts;
    private ArrayList<Crew> crews;
    private List<String> tabTitle;

    public PeopleFullCreditPagerAdapter(FragmentManager fm, List<String> tabTitle,
                                        List<Cast> castList, List<Crew> crewList) {
        super(fm);

        casts = new ArrayList<>();
        casts.addAll(castList);

        crews = new ArrayList<>();
        crews.addAll(crewList);

        this.tabTitle = new ArrayList<>();
        this.tabTitle.addAll(tabTitle);
    }

    @Override
    public Fragment getItem(int position) {
        return PeopleFullCreditViewPagerFragment.newInstance(casts, crews, position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }
}
