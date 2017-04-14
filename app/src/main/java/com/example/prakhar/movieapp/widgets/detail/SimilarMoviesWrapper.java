package com.example.prakhar.movieapp.widgets.detail;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Result;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/6/2017.
 */

public class SimilarMoviesWrapper extends FrameLayout {

    @BindView(R.id.similar_movies_list)
    RecyclerView recyclerView;

    private SimilarMoviesAdapter adapter;

    public SimilarMoviesWrapper(Context context) {
        super(context);
    }

    public SimilarMoviesWrapper(Context context, List<Result> movies,
                            SimilarMoviesAdapter.ListInteractionListener listener) {
        super(context);
        adapter = new SimilarMoviesAdapter(movies, listener);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.detail_similar_movies_wrapper, this);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
