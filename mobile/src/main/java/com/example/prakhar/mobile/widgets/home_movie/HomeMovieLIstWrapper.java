package com.example.prakhar.mobile.widgets.home_movie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.core.model.home.movie.Result;
import com.example.prakhar.mobile.R;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 2/24/2017.
 */

public class HomeMovieLIstWrapper extends FrameLayout {

    @BindView(R.id.home_list)
    RecyclerView recyclerView;
    @BindView(R.id.list_wrapper_title)
    TextView titleWrapper;

    private HomeMovieListAdapter adapter;

    public HomeMovieLIstWrapper(Context context) {
        super(context);
    }

    public HomeMovieLIstWrapper(Context context, @Nullable String title, List<Result> results,
                                int scrollPos, HomeMovieListAdapter.ListInteractionListener listener) {
        super(context);
        adapter = new HomeMovieListAdapter(results, listener);
        init(context);
        if (titleWrapper == null) {
            return;
        }

        if (title != null && !title.isEmpty()) {
            titleWrapper.setText(title);
        }

        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(scrollPos);
    }

    private void init(Context context) {
        inflate(context, R.layout.home_list_wrapper, this);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 200;
            }
        };
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
