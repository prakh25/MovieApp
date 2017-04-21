package com.example.prakhar.movieapp.ui.movie_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.prakhar.movieapp.R;
import com.example.prakhar.movieapp.ui.base.BaseActivity;

import static com.example.prakhar.movieapp.utils.Constants.ARG_ADDED_TO_WATCHLIST;
import static com.example.prakhar.movieapp.utils.Constants.ARG_MARKED_AS_FAVORITE;
import static com.example.prakhar.movieapp.utils.Constants.ARG_MOVIE_ID;
import static com.example.prakhar.movieapp.utils.Constants.ARG_POSTER_PATH;
import static com.example.prakhar.movieapp.utils.Constants.ARG_USER_RATING;

/**
 * Created by Prakhar on 3/2/2017.
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailFragment.MovieStatusListener{

    private boolean watchlistStatus = false;
    private boolean favoriteStatus = false;
    private int rating;

    public static Intent newStartIntent(Context context, Integer movieId, String posterId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(ARG_MOVIE_ID, movieId);
        intent.putExtra(ARG_POSTER_PATH, posterId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer movieId = getIntent().getIntExtra(ARG_MOVIE_ID, 0);
        String posterPath = getIntent().getStringExtra(ARG_POSTER_PATH);
        supportPostponeEnterTransition();
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, MovieDetailFragment.newInstance(movieId, posterPath))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ARG_ADDED_TO_WATCHLIST, watchlistStatus);
        returnIntent.putExtra(ARG_MARKED_AS_FAVORITE, favoriteStatus);
        returnIntent.putExtra(ARG_USER_RATING, rating);
        setResult(RESULT_OK, returnIntent);
        supportFinishAfterTransition();
        super.onBackPressed();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void movieAddedToWatchlist(boolean addedToWatchlist) {
        watchlistStatus = addedToWatchlist;
    }

    @Override
    public void movieMarkedAsFavorite(boolean markedAsFavorite) {
        favoriteStatus = markedAsFavorite;
    }

    @Override
    public void movieUserRatingChanged(int userRating) {
        rating = userRating;
    }
}
