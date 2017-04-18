package com.example.prakhar.movieapp.ui.more_movies_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.more.MoreListResult;
import com.example.prakhar.movieapp.model.tmdb.Result;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.movie_detail.MovieDetailActivity;
import com.example.prakhar.movieapp.utils.EndlessRecyclerViewOnScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.prakhar.movieapp.utils.Constants.ARG_GENRE_ID;
import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_TITLE;
import static com.example.prakhar.movieapp.utils.Constants.EXTRA_ADDED_TO_WATCHLIST;
import static com.example.prakhar.movieapp.utils.Constants.EXTRA_MARKED_AS_FAVORITE;
import static com.example.prakhar.movieapp.utils.Constants.EXTRA_USER_RATING;
import static com.example.prakhar.movieapp.utils.Constants.REQUEST_CODE;


/**
 * Created by Prakhar on 3/8/2017.
 */

public class MoreFragment extends Fragment implements MoreContract.MoreView,
        MoreAdapter.ListInteractionListener {

    @BindView(R.id.more_movies_list)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar_fragment)
    ProgressBar progressBar;
    @BindView(R.id.general_toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_message)
    ImageView messageImage;
    @BindView(R.id.tv_message)
    TextView messageText;
    @BindView(R.id.btn_try_again)
    Button tryAgainBtn;
    @BindView(R.id.message_layout)
    LinearLayout messageLayout;

    private MorePresenter morePresenter;
    private MoreAdapter moreAdapter;
    private AppCompatActivity activity;
    private int position;
    private int genreId;
    private int ID;
    private String fragmentTitle;

    public MoreFragment() {
    }

    public static MoreFragment newInstance(@Nullable String fragmentTitle, Integer id) {
        Bundle args = new Bundle();
        args.putString(ARG_TOOLBAR_TITLE, fragmentTitle);
        args.putInt(ARG_GENRE_ID, id);
        MoreFragment moreFragment = new MoreFragment();
        moreFragment.setArguments(args);

        return moreFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if (getArguments() != null) {
            fragmentTitle = getArguments().getString(ARG_TOOLBAR_TITLE);
            genreId = getArguments().getInt(ARG_GENRE_ID);
        }
        morePresenter = new MorePresenter(DataManager.getInstance());

        if(genreId == 0) {
            switch (fragmentTitle) {
                case "Most Popular Movies":
                    ID = 1;
                    moreAdapter = new MoreAdapter(false);
                    break;
                case "TMDB Top Rated":
                    ID = 2;
                    moreAdapter = new MoreAdapter(false);
                    break;
                case "Weekend Box Office":
                    ID = 3;
                    moreAdapter = new MoreAdapter(true);
                    break;
            }
        } else {
            ID = 4;
            moreAdapter = new MoreAdapter(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_movies, container, false);
        morePresenter.attachView(this);
        moreAdapter.setListInteractionListener(this);
        init(view);

        switch (ID) {
            case 1:
                if (moreAdapter.isEmpty()) {
                    morePresenter.mostPopularListRequested();
                }
                break;
            case 2:
                if (moreAdapter.isEmpty()) {
                    morePresenter.onTopRatedMoviesRequested();
                }
                break;
            case 3:
                if (moreAdapter.isEmpty()) {
                    morePresenter.onBoxOfficeRequested();
                }
                break;
            case 4:
                if(moreAdapter.isEmpty()) {
                    morePresenter.onPopularMovieByGenreRequested(genreId);
                }
                break;
        }

        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this, view);

        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(fragmentTitle);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moreAdapter);
        recyclerView.setLayoutManager(setUpLayoutManager());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(setupScrollListener(recyclerView.getLayoutManager()));

        tryAgainBtn.setOnClickListener(v -> {
            switch (ID) {
                case 1:
                    if (moreAdapter.isEmpty()) {
                        morePresenter.mostPopularListRequested();
                    }
                    break;
                case 2:
                    if (moreAdapter.isEmpty()) {
                        morePresenter.onTopRatedMoviesRequested();
                    }
                    break;
                case 3:
                    if (moreAdapter.isEmpty()) {
                        morePresenter.onBoxOfficeRequested();
                    }
                    break;
                case 4:
                    if(moreAdapter.isEmpty()) {
                        morePresenter.onPopularMovieByGenreRequested(genreId);
                    }
                    break;
            }
        });

    }

    private RecyclerView.LayoutManager setUpLayoutManager() {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(activity);
        return layoutManager;
    }

    private EndlessRecyclerViewOnScrollListener setupScrollListener(RecyclerView.LayoutManager layoutManager) {
        return new EndlessRecyclerViewOnScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                view.post(() -> {
                    if (moreAdapter.addLoadingView()) {
                        morePresenter.onListEndReached(page, ID);
                    }
                });
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean watchlistStatus = data.getBooleanExtra(EXTRA_ADDED_TO_WATCHLIST, false);
        boolean favoriteStatus = data.getBooleanExtra(EXTRA_MARKED_AS_FAVORITE, false);
        int rating = data.getIntExtra(EXTRA_USER_RATING, 0);
        if (requestCode == REQUEST_CODE) {
            moreAdapter.updateItem(watchlistStatus, favoriteStatus, rating, position);
        }
    }

    @Override
    public void onItemClick(Result result, int clickedPosition) {
        position = clickedPosition;
        startActivityForResult(MovieDetailActivity.newStartIntent(getActivity(), result.getId()), REQUEST_CODE);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void showProgress() {
        if (moreAdapter.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        moreAdapter.removeLoadingView();
    }

    @Override
    public void showEmpty() {
        messageImage.setImageResource(R.drawable.ic_error_white_24dp);
        messageText.setText(getString(R.string.nothing_to_display));
        tryAgainBtn.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showError(String errorMessage) {
        messageImage.setImageResource(R.drawable.ic_error_white_24dp);
        messageText.setText(getString(R.string.error_generic_server_error, errorMessage));
        tryAgainBtn.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        messageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMoviesList(List<MoreListResult> resultList) {
        moreAdapter.addItems(resultList);
    }

    @Override
    public void onDestroyView() {
        recyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        morePresenter.detachView();
        super.onDestroy();
    }
}
