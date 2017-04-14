package com.example.prakhar.movieapp.widgets.detail;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prakhar.movieapp.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Prakhar on 3/5/2017.
 */

public class RatingsWrapper extends FrameLayout {

    @BindView(R.id.detail_user_rating)
    FrameLayout userFrame;
    @BindView(R.id.movie_rating_user)
    TextView userMovieRating;
    @BindView(R.id.movie_rating_user_text)
    TextView userRatingText;
    @BindView(R.id.detail_user_rating_star)
    ImageView userStar;
    @BindView(R.id.movie_rating_tmdb)
    TextView tmdbMovieRating;
    @BindView(R.id.movie_rating_tmdb_votes)
    TextView tmdbMovieRatingVotes;
    @BindView(R.id.detail_trakt_rating_frame)
    FrameLayout traktRatingFrame;
    @BindView(R.id.movie_rating_trakt)
    TextView traktMovieRating;
    @BindView(R.id.movie_rating_trakt_votes)
    TextView traktMovieVotes;

    private UserRatingListener listener;
    private Integer rating;

    public RatingsWrapper(Context context) {
        super(context);
    }

    public RatingsWrapper(Context context, Integer movieId, String posterPath, String overview,
                          String backDropPath, String movieName, String releaseDate,
                          Integer voteCount, Double voteAverage,
                          Integer userRaitng, Double tmdbRating,
                          Integer tmdbVotes, Double traktRating, Integer traktVotes,
                          UserRatingListener userRatingListener) {
        super(context);

        init(context);
        initUser(userRaitng);
        initTmdb(tmdbRating, tmdbVotes);
        initTrakt(traktRating, traktVotes);

        listener = userRatingListener;

        rating = userRaitng;

        userFrame.setOnClickListener(v -> {
           listener.onRateMovieClicked(movieId, posterPath, overview,
                   backDropPath, movieName, releaseDate, voteCount, voteAverage, rating);
        });
    }

    private void init(Context context) {
        inflate(context, R.layout.detail_rating_wrapper, this);
        ButterKnife.bind(this);
    }

    private void initUser(Integer userRating) {
        if(userRating == 0) {
            userMovieRating.setText(R.string.user_rate_movie);
        } else {
            userStar.setImageResource(R.drawable.star_blue);
            userMovieRating.setText(String.format(Locale.US,"%d /10", userRating));
            userRatingText.setVisibility(VISIBLE);
            userRatingText.setText("You");
        }
    }

    private void initTmdb(Double tmdbRating, Integer tmdbVotes) {
        if(tmdbRating != 0) {
            tmdbMovieRating.setText(String.format(Locale.US, "%.1f /10", tmdbRating));
            tmdbMovieRatingVotes.setText(String.format(Locale.US, "%d", tmdbVotes));
        } else {
            tmdbMovieRating.setText(R.string.no_rating);
            tmdbMovieRatingVotes.setVisibility(GONE);
        }
    }

    private void initTrakt(Double traktRating, Integer traktVotes) {
        if(traktRating != 0) {
            traktMovieRating.setText(String.format(Locale.US, "%.1f /10", traktRating));
            traktMovieVotes.setText(String.format(Locale.US, "%d", traktVotes));
        } else {
            traktRatingFrame.setVisibility(GONE);
        }
    }

    public void updateUserRating(int userRating) {
        if(userRating == 0) {
            userMovieRating.setText(R.string.user_rate_movie);
            userRatingText.setVisibility(GONE);
            userStar.setImageResource(R.drawable.ic_star);
        } else {
            userStar.setImageResource(R.drawable.star_blue);
            userMovieRating.setText(String.format(Locale.US, "%d /10", userRating));
            userRatingText.setVisibility(VISIBLE);
            userRatingText.setText("You");
        }
        rating = userRating;
    }

    public interface UserRatingListener {
        void onRateMovieClicked(Integer movieId, String posterPath, String overview,
                                String backDropPath, String movieName, String releaseDate,
                                Integer voteCount, Double voteAverage, Integer userRating);
    }
}
