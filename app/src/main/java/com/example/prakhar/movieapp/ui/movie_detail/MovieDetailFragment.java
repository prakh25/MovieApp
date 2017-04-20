package com.example.prakhar.movieapp.ui.movie_detail;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.BelongsToCollection;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Cast;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Genre;
import com.example.prakhar.movieapp.model.tmdb.Poster;
import com.example.prakhar.movieapp.model.tmdb.Result;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.full_credits.FullCreditsActivity;
import com.example.prakhar.movieapp.ui.full_screen_image.FullScreenImageFragment;
import com.example.prakhar.movieapp.ui.more_images.MoreImagesActivity;
import com.example.prakhar.movieapp.ui.people_detail.PeopleDetailActivity;
import com.example.prakhar.movieapp.utils.Constants;
import com.example.prakhar.movieapp.utils.Utils;
import com.example.prakhar.movieapp.utils.palette.PaletteBitmap;
import com.example.prakhar.movieapp.utils.palette.PaletteBitmapTranscoder;
import com.example.prakhar.movieapp.widgets.detail.MovieCastAdapter;
import com.example.prakhar.movieapp.widgets.detail.MovieCastWrapper;
import com.example.prakhar.movieapp.widgets.detail.MovieCollectionWrapper;
import com.example.prakhar.movieapp.widgets.detail.MovieCrewWrapper;
import com.example.prakhar.movieapp.widgets.detail.MovieExternalLinksWrapper;
import com.example.prakhar.movieapp.widgets.detail.MovieImagesAdapter;
import com.example.prakhar.movieapp.widgets.detail.MovieImagesWrapper;
import com.example.prakhar.movieapp.widgets.detail.OverviewWrapper;
import com.example.prakhar.movieapp.widgets.detail.RatingsWrapper;
import com.example.prakhar.movieapp.widgets.detail.SimilarMoviesAdapter;
import com.example.prakhar.movieapp.widgets.detail.SimilarMoviesWrapper;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.prakhar.movieapp.utils.Constants.ARG_MOVIE_ID;
import static com.example.prakhar.movieapp.utils.Constants.ARG_POSTER_PATH;


/**
 * Created by Prakhar on 3/2/2017.
 */

public class MovieDetailFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener,
        MovieDetailContract.DetailView, SimilarMoviesAdapter.ListInteractionListener,
        RatingsWrapper.UserRatingListener, RatingDialogFragment.RatingDialogListener,
        UserListDialogFragment.AddToListDialogListener,
        CreateNewListDialog.CreateNewListDialogListener, MovieCastWrapper.MovieCastListener,
        MovieCrewWrapper.MovieCrewListener, MovieImagesWrapper.MovieImagesListener,
        MovieImagesAdapter.MovieImageAdapterListener, MovieCastAdapter.InteractionListener {

//    private final static String ARG_APP_BAR_EXPANDED = "appBarExpanded";

    @BindView(R.id.detail_content_frame)
    LinearLayout detailFrame;
    @BindView(R.id.detail_header_poster_frame)
    FrameLayout posterFrame;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.progress_bar_detail)
    ProgressBar contentProgress;
    @BindView(R.id.action_add_to_watchlist)
    LikeButton watchlistBtn;
    @BindView(R.id.action_add_to_watchlist_text)
    TextView watchlistText;
    @BindView(R.id.action_mark_as_favorite)
    LikeButton favoriteBtn;
    @BindView(R.id.action_mark_as_favorite_text)
    TextView favoriteText;
    @BindView(R.id.action_add_to_list)
    ImageButton addToUserListBtn;
    @BindView(R.id.detail_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.detail_toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.detail_title_container)
    LinearLayout mTitleContainer;
    @BindView(R.id.detail_header_movie_title)
    TextView movieTitle;
    @BindView(R.id.detail_header_movie_backdrop)
    ImageView movieBackdrop;
    @BindView(R.id.detail_movie_runtime)
    TextView movieRuntimeView;
    @BindView(R.id.detail_movie_certification_genre)
    TextView movieCertificateGenre;
    @BindView(R.id.detail_app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.detail_trailer_frame)
    FrameLayout trailerFrame;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detail_header_frame_layout)
    FrameLayout headerFrame;
    @BindView(R.id.toolbar_movie_title)
    TextView toolbarMovieTitle;
    @BindView(R.id.movie_detail_layout)
    CoordinatorLayout layout;
    @BindView(R.id.detail_status_wrapper_frame)
    FrameLayout statusFrame;

    @BindView(R.id.iv_message)
    ImageView messageImage;
    @BindView(R.id.tv_message)
    TextView messageText;
    @BindView(R.id.btn_try_again)
    Button tryAgainBtn;
    @BindView(R.id.message_layout)
    LinearLayout messageLayout;

    private MovieDetailPresenter movieDetailPresenter;
    private AppCompatActivity mActivity;
    private RatingsWrapper ratingsWrapper;
    private MovieStatusListener statusListener;
    private int toolbarColor;
    private Integer movieId;
    private String toolbarTitle;
    private String posterPath;

    public static MovieDetailFragment newInstance(Integer movieId, String posterPath) {

        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        args.putString(ARG_POSTER_PATH, posterPath);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            statusListener = (MovieStatusListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieDetailPresenter = new MovieDetailPresenter(DataManager.getInstance());
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARG_MOVIE_ID);
            posterPath = getArguments().getString(ARG_POSTER_PATH);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        movieDetailPresenter.attachView(this);
        init(view);
        movieDetailPresenter.onMovieRequested(movieId);

        appBarLayout.addOnOffsetChangedListener(this);
        return view;
    }

    private void init(View view) {

        ButterKnife.bind(this, view);

        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            moviePoster.setTransitionName(posterPath);
        }

        BitmapRequestBuilder<String, PaletteBitmap> glideRequest;

        glideRequest = Glide.with(mActivity).fromString().asBitmap()
                .transcode(new PaletteBitmapTranscoder(mActivity), PaletteBitmap.class);

        glideRequest.load(Constants.TMDB_IMAGE_URL + "w185" + posterPath)
                .listener(new RequestListener<String, PaletteBitmap>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<PaletteBitmap> target, boolean isFirstResource) {
                        getActivity().supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(PaletteBitmap resource, String model,
                                                   Target<PaletteBitmap> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        getActivity().supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(new ImageViewTarget<PaletteBitmap>(moviePoster) {
                    @Override
                    protected void setResource(PaletteBitmap resource) {
                        super.view.setImageBitmap(resource.bitmap);
                        toolbarColor = resource.palette.getVibrantColor(
                                resource.palette.getMutedColor(
                                        resource.palette.getDominantColor(
                                                ContextCompat.getColor(mActivity,
                                                        R.color.colorPrimary))));
                        headerFrame.setBackgroundColor(toolbarColor);
                    }
                });

        posterFrame.setVisibility(View.VISIBLE);

        tryAgainBtn.setOnClickListener(v -> movieDetailPresenter.onMovieRequested(movieId));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                moviePoster.setTransitionName(null);
            }
            mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            toolbarMovieTitle.setText(toolbarTitle);
            toolbar.setBackgroundColor(toolbarColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mActivity.getWindow().setStatusBarColor(Utils.getDarkColor(toolbarColor));
            }
        } else {
            toolbarMovieTitle.setText("");
            toolbar.setBackgroundColor(ContextCompat.getColor(mActivity, android.R.color.transparent));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mActivity.getWindow().setStatusBarColor(ContextCompat.getColor(mActivity, android.R.color.transparent));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setUpSharedElementTransition();
                setUpActivityTransition();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpSharedElementTransition() {
        Transition transition = TransitionInflater.from(mActivity)
                .inflateTransition(R.transition.arc_motion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                posterFrame.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                posterFrame.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        mActivity.getWindow().setSharedElementEnterTransition(transition);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpActivityTransition() {

        moviePoster.setTransitionName(posterPath);
        Fade fade = new Fade(Fade.OUT);
        fade.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                posterFrame.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                onDestroy();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        mActivity.getWindow().setReturnTransition(fade);
    }

    @Override
    public void showMovieHeader(String posterPath, String backDropPath,
                                String movieName, String releaseDate, List<Genre> movieGenre,
                                Integer movieRuntime) {

        if (backDropPath != null) {

            Glide.with(getActivity())
                    .load(Constants.TMDB_IMAGE_URL + "w780" + backDropPath)
                    .into(movieBackdrop);

            if (movieBackdrop.getVisibility() == View.INVISIBLE) {
                doCircularReveal();
            }

        } else {
            Glide.with(getActivity()).load(Constants.TMDB_IMAGE_URL + "w780" + posterPath)
                    .placeholder(R.drawable.movie_poster_placeholder)
                    .centerCrop()
                    .into(movieBackdrop);

            if (movieBackdrop.getVisibility() == View.INVISIBLE) {
                doCircularReveal();
            }
        }

        String title = String.format(Locale.US, "%s (%d)", movieName, Utils.getReleaseYear(releaseDate));
        movieTitle.setText(title);

        if (!movieGenre.isEmpty()) {
            movieCertificateGenre.setText(android.text.TextUtils.join(", ", getGenreNames(movieGenre)));
        }

        if (movieRuntime != null && movieRuntime != 0) {
            int hours = movieRuntime / 60;
            int minutes = movieRuntime % 60;
            String runtime = String.format(Locale.US, "%d h %d min", hours, minutes);
            movieRuntimeView.setText(runtime);
        } else {
            movieRuntimeView.setVisibility(View.GONE);
        }
        toolbarTitle = movieName;
    }

    private void doCircularReveal() {

        int centerX = (movieBackdrop.getLeft() + movieBackdrop.getRight()) / 2;
        int centerY = movieBackdrop.getTop();
        int startRadius = 0;
        int endRadius = Math.max(movieBackdrop.getWidth(), movieBackdrop.getHeight());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils
                    .createCircularReveal(movieBackdrop, centerX, centerY, startRadius, endRadius);
            animator.setDuration(500);
            movieBackdrop.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            movieBackdrop.setVisibility(View.VISIBLE);
        }
    }

    private List<String> getGenreNames(List<Genre> genreList) {
        List<String> strings = new ArrayList<>();
        for (Genre genre : genreList) {
            strings.add(genre.getName());
        }
        return strings;
    }

    @Override
    public void showMovieStatus(Integer movieId, String posterPath, String overview,
                                String backDropPath, String movieName, String releaseDate,
                                Integer voteCount, Double voteAverage, boolean isAddedToWatchlist,
                                boolean isMarkedAsFavorite) {

        if (isAddedToWatchlist) {
            watchlistBtn.setLiked(true);
            watchlistText.setText(R.string.added_to_watchlist);
            statusListener.movieAddedToWatchlist(true);
        } else {
            watchlistBtn.setLiked(false);
            watchlistText.setText(R.string.add_to_watchlist);
            statusListener.movieAddedToWatchlist(false);
        }

        if (isMarkedAsFavorite) {
            favoriteBtn.setLiked(true);
            favoriteText.setText(R.string.marked_as_favorite);
            statusListener.movieMarkedAsFavorite(true);
        } else {
            favoriteBtn.setLiked(false);
            favoriteText.setText(R.string.mark_as_favorite);
            statusListener.movieMarkedAsFavorite(false);
        }

        watchlistClickListener(movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);

        favoriteClickListener(movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);

        addToClickListener(movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);

        statusFrame.setVisibility(View.VISIBLE);

    }

    private void watchlistClickListener(Integer movieId, String posterPath, String overview,
                                        String backDropPath, String movieName,
                                        String releaseDate, Integer voteCount,
                                        Double voteAverage) {

        watchlistBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                watchlistText.setText(R.string.added_to_watchlist);
                movieDetailPresenter.onAddToWatchlistClicked(movieId, posterPath, overview,
                        backDropPath, movieName, releaseDate, voteCount, voteAverage);
                statusListener.movieAddedToWatchlist(true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                watchlistText.setText(R.string.add_to_watchlist);
                movieDetailPresenter.onRemoveFromWatchlistClicked(movieId);
                statusListener.movieAddedToWatchlist(false);
            }
        });
    }

    private void favoriteClickListener(Integer movieId, String posterPath, String overview,
                                       String backDropPath, String movieName,
                                       String releaseDate, Integer voteCount,
                                       Double voteAverage) {

        favoriteBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                favoriteText.setText(R.string.marked_as_favorite);
                movieDetailPresenter.onAddMarkAsFavoriteClicked(movieId, posterPath,
                        overview, backDropPath, movieName, releaseDate, voteCount, voteAverage);
                statusListener.movieMarkedAsFavorite(true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                favoriteText.setText(R.string.mark_as_favorite);
                statusListener.movieMarkedAsFavorite(false);
                movieDetailPresenter.onRemoveFromFavoriteList(movieId);
            }
        });
    }

    private void addToClickListener(Integer movieId, String posterPath, String overview,
                                    String backDropPath, String movieName, String releaseDate,
                                    Integer voteCount, Double voteAverage) {

        addToUserListBtn.setOnClickListener(v ->
                movieDetailPresenter.onAddToListClicked(movieId, posterPath, overview,
                        backDropPath, movieName, releaseDate, voteCount, voteAverage));
    }

    @Override
    public void showMovieDetails(String releaseDate, String region, String tagLine, String overView) {
        OverviewWrapper overviewWrapper = new OverviewWrapper(mActivity,
                releaseDate, region, tagLine, overView);
        detailFrame.addView(overviewWrapper);
    }

    @Override
    public void showRatings(Integer movieId, String posterPath, String overview,
                            String backDropPath, String movieName, String releaseDate,
                            Integer voteCount, Double voteAverage,
                            Integer userRating, Double tmdbRating,
                            Integer tmdbVotes, Double traktRating, Integer traktVotes) {

        ratingsWrapper = new RatingsWrapper(mActivity, movieId, posterPath, overview,
                backDropPath, movieName, releaseDate, voteCount, voteAverage, userRating,
                tmdbRating, tmdbVotes, traktRating, traktVotes, this);

        detailFrame.addView(ratingsWrapper);

        statusListener.movieUserRatingChanged(userRating);
    }

    @Override
    public void showMovieTrailer(String key) {
        trailerFrame.setVisibility(View.VISIBLE);
        trailerFrame.setOnClickListener(v -> {
            startActivity(TrailerActivity.newStartIntent(mActivity, key));
            mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    public void showSimilarMovies(List<Result> resultList) {
        SimilarMoviesWrapper similarMoviesWrapper = new SimilarMoviesWrapper(getActivity(), resultList, this);
        detailFrame.addView(similarMoviesWrapper);
    }

    @Override
    public void showMovieCast(List<Cast> castList) {
        MovieCastWrapper movieCastWrapper = new MovieCastWrapper(mActivity, castList, this, this);
        detailFrame.addView(movieCastWrapper);
    }

    @Override
    public void showMovieCrew(List<Crew> directorsList, List<Crew> writesList, List<Crew> screenplayList) {
        MovieCrewWrapper movieCrew = new MovieCrewWrapper(mActivity, directorsList, writesList,
                screenplayList, this);
        detailFrame.addView(movieCrew);
    }

    @Override
    public void showAddToListDialog(List<String> userListsName, Result result) {

        FragmentManager fragmentManager = getFragmentManager();
        UserListDialogFragment fragment = UserListDialogFragment.newInstance(userListsName, result);

        fragment.setTargetFragment(MovieDetailFragment.this, 300);
        fragment.show(fragmentManager, "userListFragment");
    }

    @Override
    public void showMovieImages(List<Poster> imageList, Integer imageNumber, boolean showViewAll) {
        String title = String.format(Locale.US, "Images (%d)", imageNumber);
        MovieImagesWrapper movieImagesWrapper = new MovieImagesWrapper(mActivity, imageList,
                title, showViewAll, this, this);
        detailFrame.addView(movieImagesWrapper);
    }

    @Override
    public void showBelongToCollection(BelongsToCollection belongsToCollection) {
        MovieCollectionWrapper wrapper = new MovieCollectionWrapper(mActivity, belongsToCollection);
        detailFrame.addView(wrapper);
    }

    @Override
    public void showExternalLinks(String movieHomepage, Integer tmdbId, String imdbId, String movieName) {
        MovieExternalLinksWrapper externalLinksWrapper = new MovieExternalLinksWrapper(mActivity,
                movieHomepage, tmdbId, imdbId, movieName);
        detailFrame.addView(externalLinksWrapper);
    }

    @Override
    public void onSimilarListItemClick(Result result, View sharedElementView, int position, ImageView view) {

        startActivity(MovieDetailActivity.newStartIntent(mActivity, result.getId(),
                ViewCompat.getTransitionName(view)), makeTransitionBundle(view));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setUpTransitionAnimation();
        }
    }

    private Bundle makeTransitionBundle(ImageView sharedElementView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)).toBundle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpTransitionAnimation() {
        Transition transition = TransitionInflater.from(mActivity)
                .inflateTransition(R.transition.arc_motion);

        mActivity.getWindow().setSharedElementReenterTransition(transition);
        mActivity.getWindow().setSharedElementExitTransition(transition);

        mActivity.getWindow().setAllowEnterTransitionOverlap(true);
        mActivity.getWindow().setAllowReturnTransitionOverlap(true);
    }

    @Override
    public void onRateMovieClicked(Integer movieId, String posterPath, String overview,
                                   String backDropPath, String movieName, String releaseDate,
                                   Integer voteCount, Double voteAverage, Integer userRating) {

        Result result = new Result();

        result.setId(movieId);
        result.setPosterPath(posterPath);
        result.setOverview(overview);
        result.setBackdropPath(backDropPath);
        result.setTitle(movieName);
        result.setReleaseDate(releaseDate);
        result.setVoteCount(voteCount);
        result.setVoteAverage(voteAverage);

        FragmentManager fragmentManager = getFragmentManager();
        RatingDialogFragment fragment = RatingDialogFragment.newInstance(result, userRating);
        fragment.setTargetFragment(MovieDetailFragment.this, 300);
        fragment.show(fragmentManager, "ratingDialog");
    }

    @Override
    public void onRatingSave(Result result, int rating) {
        Toast.makeText(getContext(), Integer.toString(rating), Toast.LENGTH_SHORT).show();
        ratingsWrapper.updateUserRating(rating);
        movieDetailPresenter.onSaveMovieRatingClicked(result.getId(), result.getPosterPath(),
                result.getOverview(), result.getBackdropPath(), result.getTitle(),
                result.getReleaseDate(), result.getVoteCount(), result.getVoteAverage(), rating);
        statusListener.movieUserRatingChanged(rating);
    }

    @Override
    public void onRatingDelete() {
        ratingsWrapper.updateUserRating(0);
        movieDetailPresenter.onDeleteMovieRatingClicked(movieId);
        statusListener.movieUserRatingChanged(0);
    }

    @Override
    public void showProgress() {
        if (contentProgress.getVisibility() != View.VISIBLE) {
            contentProgress.setVisibility(View.VISIBLE);
        }
        detailFrame.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        contentProgress.setVisibility(View.GONE);
        detailFrame.setVisibility(View.VISIBLE);
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
        headerFrame.setVisibility(View.GONE);
        messageImage.setImageResource(R.drawable.ic_error_white_24dp);
        messageText.setText(getString(R.string.error_generic_server_error, errorMessage));
        tryAgainBtn.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        messageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        detailFrame.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void createNewListClick(Result result) {
        FragmentManager fragmentManager = getFragmentManager();
        CreateNewListDialog fragment = CreateNewListDialog.newInstance(result);
        fragment.setTargetFragment(MovieDetailFragment.this, 300);
        fragment.show(fragmentManager, "createNewListDialog");
    }

    @Override
    public void addMovieToList(String name, int listId, Result result) {
        Toast.makeText(mActivity, "Movie Added To List " + name, Toast.LENGTH_SHORT).show();
        movieDetailPresenter.onAddMovieToList(listId, result.getId(), result.getPosterPath(),
                result.getOverview(), result.getBackdropPath(), result.getTitle(),
                result.getReleaseDate(), result.getVoteCount(), result.getVoteAverage());
    }

    @Override
    public void createNewList(String title, String description, Result result) {
        Toast.makeText(mActivity, "Movie Added To List " + title, Toast.LENGTH_SHORT).show();
        movieDetailPresenter.onCreateNewListRequested(title, description,
                result.getId(), result.getPosterPath(),
                result.getOverview(), result.getBackdropPath(), result.getTitle(),
                result.getReleaseDate(), result.getVoteCount(), result.getVoteAverage());
    }

    @Override
    public void cannotCreateList() {
        Toast.makeText(mActivity, "Please provide List Title", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFullCastButtonClicked() {
        ArrayList<Cast> casts = new ArrayList<>();
        ArrayList<Crew> crews = new ArrayList<>();
        casts.addAll(movieDetailPresenter.getCastList());
        startActivity(FullCreditsActivity.newStartIntent(mActivity, crews, casts, "Cast", toolbarColor));
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onFullCrewButtonClicked() {
        ArrayList<Cast> casts = new ArrayList<>();
        ArrayList<Crew> crews = new ArrayList<>();
        crews.addAll(movieDetailPresenter.getCrewList());
        startActivity(FullCreditsActivity.newStartIntent(mActivity, crews, casts, "Crew", toolbarColor));
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void startPeopleDetailActivity(Integer personId, String profilePath) {
        startActivity(PeopleDetailActivity.newStartIntent(mActivity, personId, profilePath));
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void startFullCreditActivity(List<Crew> crewList, String title) {
        ArrayList<Cast> casts = new ArrayList<>();
        ArrayList<Crew> crews = new ArrayList<>();
        crews.addAll(crewList);
        startActivity(FullCreditsActivity.newStartIntent(mActivity, crews, casts, title, toolbarColor));
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onMoreImagesRequested() {
        ArrayList<Poster> imageList = new ArrayList<>();
        imageList.addAll(movieDetailPresenter.getMovieImages());
        startActivity(MoreImagesActivity.newStartIntent(mActivity, imageList,
                "Images", toolbarColor));
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onImageClicked(int position) {
        ArrayList<Poster> imageList = new ArrayList<>();
        imageList.addAll(movieDetailPresenter.getMovieImages());
        FragmentManager fragmentManager = getFragmentManager();
        FullScreenImageFragment fragment = FullScreenImageFragment.newInstance(imageList, position);
        fragment.setTargetFragment(MovieDetailFragment.this, 300);
        fragment.show(fragmentManager, "fullScreenImage");
    }

    @Override
    public void onPersonClicked(Integer id, String profilePath, int clickedPosition) {
        startActivity(PeopleDetailActivity.newStartIntent(mActivity, id, profilePath));
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    interface MovieStatusListener {
        void movieAddedToWatchlist(boolean addedToWatchlist);

        void movieMarkedAsFavorite(boolean markedAsFavorite);

        void movieUserRatingChanged(int userRating);
    }

    @Override
    public void onDestroyView() {
        movieDetailPresenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        movieDetailPresenter.detachView();
        super.onDestroy();
    }
}
