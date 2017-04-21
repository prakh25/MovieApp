package com.example.prakhar.movieapp.ui.more_movies_list;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.more_movie_list.MovieListResult;
import com.example.prakhar.movieapp.model.home.movie.Result;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.movie_detail.MovieDetailActivity;
import com.example.prakhar.movieapp.utils.EndlessRecyclerViewOnScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.prakhar.movieapp.utils.Constants.ARG_ADDED_TO_WATCHLIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_GENRE_ID;
import static com.example.prakhar.movieapp.utils.Constants.ARG_MARKED_AS_FAVORITE;
import static com.example.prakhar.movieapp.utils.Constants.ARG_TOOLBAR_TITLE;
import static com.example.prakhar.movieapp.utils.Constants.ARG_USER_RATING;
import static com.example.prakhar.movieapp.utils.Constants.REQUEST_CODE;


/**
 * Created by Prakhar on 3/8/2017.
 */

public class MovieListFragment extends Fragment implements MovieListContract.MoreView,
        MovieListAdapter.ListInteractionListener {

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

    private MovieListPresenter movieListPresenter;
    private MovieListAdapter movieListAdapter;
    private AppCompatActivity activity;
    private int position;
    private int genreId;
    private int ID;
    private String fragmentTitle;

    public static MovieListFragment newInstance(@Nullable String fragmentTitle, Integer id) {
        Bundle args = new Bundle();
        args.putString(ARG_TOOLBAR_TITLE, fragmentTitle);
        args.putInt(ARG_GENRE_ID, id);
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setArguments(args);

        return movieListFragment;
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
        movieListPresenter = new MovieListPresenter(DataManager.getInstance());

        if(genreId == 0) {
            switch (fragmentTitle) {
                case "Most Popular Movies":
                    ID = 1;
                    movieListAdapter = new MovieListAdapter(false);
                    break;
                case "TMDB Top Rated":
                    ID = 2;
                    movieListAdapter = new MovieListAdapter(false);
                    break;
                case "Weekend Box Office":
                    ID = 3;
                    movieListAdapter = new MovieListAdapter(true);
                    break;
                default:
                    break;
            }
        } else {
            ID = 4;
            movieListAdapter = new MovieListAdapter(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_movies, container, false);
        movieListPresenter.attachView(this);
        movieListAdapter.setListInteractionListener(this);
        init(view);

        switch (ID) {
            case 1:
                if (movieListAdapter.isEmpty()) {
                    movieListPresenter.mostPopularListRequested();
                }
                break;
            case 2:
                if (movieListAdapter.isEmpty()) {
                    movieListPresenter.onTopRatedMoviesRequested();
                }
                break;
            case 3:
                if (movieListAdapter.isEmpty()) {
                    movieListPresenter.onBoxOfficeRequested();
                }
                break;
            case 4:
                if(movieListAdapter.isEmpty()) {
                    movieListPresenter.onPopularMovieByGenreRequested(genreId);
                }
                break;
            default:
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
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setLayoutManager(setUpLayoutManager());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(setupScrollListener(recyclerView.getLayoutManager()));

        tryAgainBtn.setOnClickListener(v -> {
            switch (ID) {
                case 1:
                    if (movieListAdapter.isEmpty()) {
                        movieListPresenter.mostPopularListRequested();
                    }
                    break;
                case 2:
                    if (movieListAdapter.isEmpty()) {
                        movieListPresenter.onTopRatedMoviesRequested();
                    }
                    break;
                case 3:
                    if (movieListAdapter.isEmpty()) {
                        movieListPresenter.onBoxOfficeRequested();
                    }
                    break;
                case 4:
                    if(movieListAdapter.isEmpty()) {
                        movieListPresenter.onPopularMovieByGenreRequested(genreId);
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
                    if (movieListAdapter.addLoadingView()) {
                        movieListPresenter.onListEndReached(page, ID);
                    }
                });
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean watchlistStatus = data.getBooleanExtra(ARG_ADDED_TO_WATCHLIST, false);
        boolean favoriteStatus = data.getBooleanExtra(ARG_MARKED_AS_FAVORITE, false);
        int rating = data.getIntExtra(ARG_USER_RATING, 0);
        if (requestCode == REQUEST_CODE) {
            movieListAdapter.updateItem(watchlistStatus, favoriteStatus, rating, position);
        }
    }

    @Override
    public void onItemClick(Result result, int clickedPosition, ImageView sharedImageView) {
        position = clickedPosition;
        startActivityForResult(MovieDetailActivity.newStartIntent(getActivity(), result.getId(),
                ViewCompat.getTransitionName(sharedImageView)), REQUEST_CODE,
                makeTransitionBundle(sharedImageView));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpTransitionAnimation();
        }
    }

    private Bundle makeTransitionBundle(ImageView sharedElementView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)).toBundle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransitionAnimation() {
        Transition transition = TransitionInflater.from(activity)
                .inflateTransition(R.transition.arc_motion);
        activity.getWindow().setSharedElementReenterTransition(transition);
    }

    @Override
    public void showProgress() {
        if (movieListAdapter.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        movieListAdapter.removeLoadingView();
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
    public void showMoviesList(List<MovieListResult> resultList) {

        movieListAdapter.addItems(resultList);
    }

    @Override
    public void onDestroyView() {
        recyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        movieListPresenter.detachView();
        super.onDestroy();
    }
}
