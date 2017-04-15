package com.example.prakhar.movieapp.ui.people_full_credit;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.people_detail.Cast;
import com.example.prakhar.movieapp.model.people_detail.Crew;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.prakhar.movieapp.utils.Constants.ARG_CAST_LIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_CREW_LIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_COLOR;

/**
 * Created by Prakhar on 4/6/2017.
 */

public class PeopleFullCreditFragment extends Fragment {

    @BindView(R.id.people_full_credit_view_pager)
    ViewPager viewPager;
    @BindView(R.id.people_full_credit_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.people_full_credit_toolbar)
    Toolbar toolbar;
    @BindView(R.id.people_full_credit_app_bar)
    AppBarLayout appBarLayout;

    private List<Cast> castList;
    private List<Crew> crewList;
    private int color;
    private Unbinder unbinder;

    public PeopleFullCreditFragment() {
    }

    public static PeopleFullCreditFragment newInstance(ArrayList<Cast> casts, ArrayList<Crew> crews,
                                                       int color) {
        Bundle args = new Bundle();

        args.putParcelableArrayList(ARG_CAST_LIST, casts);
        args.putParcelableArrayList(ARG_CREW_LIST, crews);
        args.putInt(ARG_TOOLBAR_COLOR, color);

        PeopleFullCreditFragment fragment = new PeopleFullCreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
        if(getArguments() != null) {
            color = getArguments().getInt(ARG_TOOLBAR_COLOR);
            castList.addAll(getArguments().getParcelableArrayList(ARG_CAST_LIST));
            crewList.addAll(getArguments().getParcelableArrayList(ARG_CREW_LIST));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_full_credit, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setStatusBarColor(color);
//        }

        toolbar.setTitle("Full Credits");

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.7f; // value component
        int toolbarColor = Color.HSVToColor(hsv);

        appBarLayout.setBackgroundColor(toolbarColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Color.colorToHSV(color, hsv);
            hsv[2] *= 0.6f; // value component
            int statusBarColor = Color.HSVToColor(hsv);
            activity.getWindow().setStatusBarColor(statusBarColor);
        }

        List<String> tabTitles = new ArrayList<>();
        tabTitles.add(String.format(Locale.US, "Cast (%d)", castList.size()));
        tabTitles.add(String.format(Locale.US, "Crew (%d)", crewList.size()));

        viewPager.setAdapter(new PeopleFullCreditPagerAdapter(activity.getSupportFragmentManager(),
                tabTitles, castList, crewList));

        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setSelectedTabIndicatorColor(color);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
