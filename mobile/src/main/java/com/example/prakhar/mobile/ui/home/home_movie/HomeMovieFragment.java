package com.example.prakhar.mobile.ui.home.home_movie;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.core.model.home.movie.Result;
import com.example.core.network.DataManager;
import com.example.core.ui.home.home_movie.HomeMovieContract;
import com.example.core.ui.home.home_movie.HomeMoviePresenter;
import com.example.prakhar.mobile.R;
import com.example.prakhar.mobile.ui.genre.GenreActivity;
import com.example.prakhar.mobile.ui.more_movies_list.MovieListActivity;
import com.example.prakhar.mobile.ui.movie_detail.MovieDetailActivity;
import com.example.prakhar.mobile.widgets.home_movie.HomeLinksWrapper;
import com.example.prakhar.mobile.widgets.home_movie.HomeMovieLIstWrapper;
import com.example.prakhar.mobile.widgets.home_movie.HomeMovieListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Prakhar on 2/21/2017.
 */

public class HomeMovieFragment extends Fragment implements HomeMovieContract.HomeMovieView,
        HomeMovieListAdapter.ListInteractionListener, HomeLinksWrapper.LinkInteractionListener {

    private static final String SCROLL_VIEW_Y_POS = "scrollViewYPosition";
    private static final String NOW_PLAYING_SCROLL_POS = "nowPlayingScrollPos";
    private static final String COMING_SOON_SCROLL_POS = "comingSoonScrollPos";

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

    private int scroll_y;
    private RecyclerView nowPLayingRecyclerView;
    private RecyclerView comingSoonRecyclerView;

    private int nowPlayingScrollPos = 0;
    private int comingSoonScrollPos = 0;

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCROLL_VIEW_Y_POS, scrollView.getScrollY());
        outState.putInt(NOW_PLAYING_SCROLL_POS, ((LinearLayoutManager) nowPLayingRecyclerView
                .getLayoutManager()).findFirstCompletelyVisibleItemPosition());
        outState.putInt(COMING_SOON_SCROLL_POS, ((LinearLayoutManager) comingSoonRecyclerView
                .getLayoutManager()).findFirstCompletelyVisibleItemPosition());

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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            scroll_y = savedInstanceState.getInt(SCROLL_VIEW_Y_POS);

            nowPlayingScrollPos = savedInstanceState.getInt(NOW_PLAYING_SCROLL_POS);
            comingSoonScrollPos = savedInstanceState.getInt(COMING_SOON_SCROLL_POS);

            ViewTreeObserver treeObserver = scrollView.getViewTreeObserver();
            treeObserver.addOnGlobalLayoutListener(() ->
                    scrollView.scrollTo(0, scroll_y)
            );
        }
    }

    @Override
    public void showComingSoonMovies(List<Result> resultList) {
        HomeMovieLIstWrapper comingSoonWrapper = new HomeMovieLIstWrapper(activity,
                getString(R.string.coming_soon), resultList, comingSoonScrollPos, this);
        layout.addView(comingSoonWrapper);
        comingSoonRecyclerView = (RecyclerView) comingSoonWrapper.findViewById(R.id.home_list);
    }

    @Override
    public void showNowPlayingMovies(List<Result> resultList) {
        HomeMovieLIstWrapper nowPlayingWrapper = new HomeMovieLIstWrapper(activity,
                getString(R.string.now_playing), resultList, nowPlayingScrollPos, this);
        layout.addView(nowPlayingWrapper);
        nowPLayingRecyclerView = (RecyclerView) nowPlayingWrapper.findViewById(R.id.home_list);
    }

    @Override
    public void showLinks() {
        HomeLinksWrapper homeLinksWrapper = new HomeLinksWrapper(activity, this);
        layout.addView(homeLinksWrapper);
    }

    @Override
    public void onListItemClick(Result result, int position, ImageView poster) {

        startActivity(MovieDetailActivity.newStartIntent(activity, result.getId(),
                ViewCompat.getTransitionName(poster)), makeTransitionBundle(poster));

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
        startActivity(MovieListActivity.newStartIntent(activity, title, 0));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onGenreFrameClick(String title) {
        startActivity(GenreActivity.newStartIntent(activity, title));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

