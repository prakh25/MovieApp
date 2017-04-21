package com.example.prakhar.movieapp.ui.full_credits;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Cast;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew;
import com.example.prakhar.movieapp.ui.people_detail.PeopleDetailActivity;
import com.example.prakhar.movieapp.utils.Utils;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.prakhar.movieapp.utils.Constants.ARG_CAST_LIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_CREW_LIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_COLOR;
import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_TITLE;

/**
 * Created by Prakhar on 3/30/2017.
 */

public class FullCreditFragment extends Fragment implements FullCreditAdapter.FullCreditListener {

    @BindView(R.id.general_toolbar)
    Toolbar toolbar;
    @BindView(R.id.full_crew_list)
    RecyclerView recyclerView;

    private String title;
    private int color;
    private List<Crew> crewList;
    private List<Cast> castList;
    private AppCompatActivity mActivity;
    private Unbinder unbinder;
    private FullCreditAdapter adapter;

    public static FullCreditFragment newInstance(@Nullable ArrayList<Crew> creditList,
                                                 @Nullable ArrayList<Cast> casts,
                                                 String title, int color) {

        Bundle args = new Bundle();

        args.putParcelableArrayList(ARG_CREW_LIST, creditList);
        args.putString(ARG_TOOLBAR_TITLE, title);
        args.putInt(ARG_TOOLBAR_COLOR, color);
        args.putParcelableArrayList(ARG_CAST_LIST, casts);
        FullCreditFragment fragment = new FullCreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crewList = new ArrayList<>();
        castList = new ArrayList<>();
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TOOLBAR_TITLE);
            color = getArguments().getInt(ARG_TOOLBAR_COLOR);
            crewList.addAll(getArguments().getParcelableArrayList(ARG_CREW_LIST));
            castList.addAll(getArguments().getParcelableArrayList(ARG_CAST_LIST));
        }

        switch (title) {
            case "Cast":
                adapter = new FullCreditAdapter(0);
                break;
            case "Crew":
                adapter = new FullCreditAdapter(1);
                break;
            case "Directed By":
                adapter = new FullCreditAdapter(1);
                break;
            case "Written By":
                adapter = new FullCreditAdapter(1);
                break;
            case "ScreenPlay Credits":
                adapter = new FullCreditAdapter(1);
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_credits, container, false);
        adapter.setUpFullCreditListener(this);
        init(view);
        switch (title) {
            case "Cast":
                adapter.addCasts(castList);
                break;
            case "Crew":
                adapter.addCrews(crewList);
                break;
            case "Directed By":
                adapter.addCrews(crewList);
                break;
            case "Written By":
                adapter.addCrews(crewList);
                break;
            case "ScreenPlay Credits":
                adapter.addCrews(crewList);
                break;
        }

        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivity.getWindow().setStatusBarColor(Utils.getDarkColor(color));
        }
        toolbar.setTitle(title);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPersonClicked(Integer personId, String profilePath, int clickedPosition) {
        startActivity(PeopleDetailActivity.newStartIntent(mActivity, personId, profilePath));
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
