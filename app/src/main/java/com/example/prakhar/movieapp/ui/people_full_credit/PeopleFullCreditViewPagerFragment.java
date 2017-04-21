package com.example.prakhar.movieapp.ui.people_full_credit;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.people_detail.Cast;
import com.example.prakhar.movieapp.model.people_detail.Crew;
import com.example.prakhar.movieapp.ui.movie_detail.MovieDetailActivity;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.prakhar.movieapp.utils.Constants.ARG_CAST_LIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_CREW_LIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_VIEW_TYPE;

/**
 * Created by Prakhar on 4/6/2017.
 */

public class PeopleFullCreditViewPagerFragment extends Fragment
        implements PeopleFullCreditAdapter.PeopleFullCreditListener {

    @BindView(R.id.people_full_credit_list)
    RecyclerView recyclerView;
    @BindView(R.id.people_full_credit_empty_text)
    TextView emptyTextMessage;

    private List<Cast> castList;
    private List<Crew> crewList;
    private int viewType;
    private PeopleFullCreditAdapter adapter;
    private Unbinder unbinder;

    public PeopleFullCreditViewPagerFragment() {
    }

    public static PeopleFullCreditViewPagerFragment newInstance(@Nullable ArrayList<Cast> castList,
                                                                @Nullable ArrayList<Crew> crewList,
                                                                Integer viewType) {

        Bundle args = new Bundle();

        args.putParcelableArrayList(ARG_CAST_LIST, castList);
        args.putParcelableArrayList(ARG_CREW_LIST, crewList);
        args.putInt(ARG_VIEW_TYPE, viewType);

        PeopleFullCreditViewPagerFragment fragment = new PeopleFullCreditViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
        if(getArguments() != null) {
            viewType = getArguments().getInt(ARG_VIEW_TYPE);
            castList.addAll(getArguments().getParcelableArrayList(ARG_CAST_LIST));
            crewList.addAll(getArguments().getParcelableArrayList(ARG_CREW_LIST));
        }
        adapter = new PeopleFullCreditAdapter(viewType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_full_credit_tab, container, false);
        adapter.setUpFullCreditListener(this);
        init(view);
        switch (viewType) {
            case 0:
                if(!castList.isEmpty()) {
                    adapter.addCasts(castList);
                } else {
                    recyclerView.setAdapter(null);
                    emptyTextMessage.setVisibility(View.VISIBLE);
                    emptyTextMessage.setText(R.string.no_cast_credits);
                }
                break;
            case 1:
                if(!crewList.isEmpty()) {
                    adapter.addCrews(crewList);
                } else {
                    recyclerView.setAdapter(null);
                    emptyTextMessage.setVisibility(View.VISIBLE);
                    emptyTextMessage.setText(R.string.no_crew_credits);
                }
                break;
        }

        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);

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
    public void onPersonClicked(Integer movieId, int clickedPosition, ImageView shareImageView) {
        startActivity(MovieDetailActivity.newStartIntent(getActivity(),movieId,
                ViewCompat.getTransitionName(shareImageView)),
                makeTransitionBundle(shareImageView));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpTransitionAnimation();
        }
    }

    private Bundle makeTransitionBundle(ImageView sharedElementView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)).toBundle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransitionAnimation() {
        Transition transition = TransitionInflater.from(getActivity())
                .inflateTransition(R.transition.arc_motion);
        getActivity().getWindow().setSharedElementReenterTransition(transition);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
