package com.example.prakhar.movieapp.ui.home.home_movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.tmdb.Result;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.genre.GenreActivity;
import com.example.prakhar.movieapp.ui.more_movies_list.MoreActivity;
import com.example.prakhar.movieapp.ui.movie_detail.MovieDetailActivity;
import com.example.prakhar.movieapp.widgets.home_movie.HomeLinksWrapper;
import com.example.prakhar.movieapp.widgets.home_movie.HomeMovieLIstWrapper;
import com.example.prakhar.movieapp.widgets.home_movie.HomeMovieListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Prakhar on 2/21/2017.
 */

public class HomeMovieFragment extends Fragment implements HomeMovieContract.HomeMovieView,
        HomeMovieListAdapter.ListInteractionListener, HomeLinksWrapper.LinkInteractionListener {

    @BindView(R.id.home_movies_content_frame)
    LinearLayout layout;
    @BindView(R.id.home_movies_scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar_fragment)
    ProgressBar contentProgress;

    @BindView(R.id.iv_message)
    ImageView messageImage;
    @BindView(R.id.tv_message)
    TextView messageText;
    @BindView(R.id.btn_try_again)
    Button tryAgainBtn;
    @BindView(R.id.message_layout)
    LinearLayout messageLayout;

    private AppCompatActivity activity;
    private HomeMoviePresenter homeMoviePresenter;
    private Unbinder unbinder;

    public HomeMovieFragment() {
    }

    public static HomeMovieFragment newInstance() {

        Bundle args = new Bundle();
        HomeMovieFragment fragment = new HomeMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        homeMoviePresenter = new HomeMoviePresenter(DataManager.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_movies, container, false);
        homeMoviePresenter.attachView(this);
        init(view);
        homeMoviePresenter.onInitializeRequest();
        return view;
    }

    void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        activity = (AppCompatActivity) getActivity();

        tryAgainBtn.setOnClickListener(v -> homeMoviePresenter.onInitializeRequest());
    }

    @Override
    public void showComingSoonMovies(List<Result> resultList) {
        HomeMovieLIstWrapper nowPlayingWrapper = new HomeMovieLIstWrapper(activity, "Coming Soon",
                resultList, this);
        layout.addView(nowPlayingWrapper);
    }

    @Override
    public void showNowPlayingMovies(List<Result> resultList) {
        HomeMovieLIstWrapper nowPlayingWrapper = new HomeMovieLIstWrapper(activity, "In Theaters",
                resultList, this);
        layout.addView(nowPlayingWrapper);
    }

    @Override
    public void showLinks() {
        HomeLinksWrapper homeLinksWrapper = new HomeLinksWrapper(activity, this);
        layout.addView(homeLinksWrapper);
    }

    @Override
    public void onListItemClick(Result result, int position) {
        startActivity(MovieDetailActivity.newStartIntent(activity, result.getId()));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void showProgress() {
        if(contentProgress.getVisibility() != View.VISIBLE) {
            contentProgress.setVisibility(View.VISIBLE);
        }
        layout.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        contentProgress.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
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
        layout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        homeMoviePresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onFrameClick(String title) {
        startActivity(MoreActivity.newStartIntent(activity, title, 0));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onGenreFrameClick(String title) {
        startActivity(GenreActivity.newStartIntent(activity, title));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

