package com.example.prakhar.movieapp.ui.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.ui.base.BaseActivity;

import timber.log.Timber;

/**
 * Created by Prakhar on 3/2/2017.
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailFragment.MovieStatusListener{

    private static final String EXTRA_ID = "movieId";
    private static final String EXTRA_ADDED_TO_WATCHLIST = "addedToWatchlist";
    private static final String EXTRA_MARKED_AS_FAVORITE = "markedAsFavorite";
    private static final String EXTRA_USER_RATING = "userRating";

    private boolean watchlistStatus = false;
    private boolean favoriteStatus = false;
    private int rating;

    public static Intent newStartIntent(Context context, Integer movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_ID, movieId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer movieId = getIntent().getIntExtra(EXTRA_ID, 0);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, MovieDetailFragment.newInstance(movieId))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_ADDED_TO_WATCHLIST, watchlistStatus);
        returnIntent.putExtra(EXTRA_MARKED_AS_FAVORITE, favoriteStatus);
        returnIntent.putExtra(EXTRA_USER_RATING, rating);
        setResult(RESULT_OK, returnIntent);
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void movieAddedToWatchlist(boolean addedToWatchlist) {
        watchlistStatus = addedToWatchlist;
        Timber.i("Watchlist status " + watchlistStatus);
    }

    @Override
    public void movieMarkedAsFavorite(boolean markedAsFavorite) {
        favoriteStatus = markedAsFavorite;
        Timber.i("Favorite Status " + favoriteStatus);
    }

    @Override
    public void movieUserRatingChanged(int userRating) {
        rating = userRating;
    }
}
