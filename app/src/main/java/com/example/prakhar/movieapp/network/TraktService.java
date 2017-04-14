package com.example.prakhar.movieapp.network;

import com.example.prakhar.movieapp.model.movie_detail.trakt.TraktMovieRating;
import com.example.prakhar.movieapp.model.trakt.box_office.BoxOffice;
import com.example.prakhar.movieapp.model.trakt.movie_summary.TraktMovieSummary;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Prakhar on 2/28/2017.
 */

public interface TraktService {

    @Headers({"Content-Type: application/json",
            "trakt-api-version: 2"})
    @GET("movies/boxoffice")
    Observable<List<BoxOffice>> getWeekendBoxOffice(@Header("trakt-api-key") String traktClientId);

    @Headers({"Content-Type: application/json",
            "trakt-api-version: 2"})
    @GET("movies/{id}")
    Observable<TraktMovieSummary> getMovieSummary(@Header("trakt-api-key") String traktClientId,
                                                  @Path("id") String movieId,
                                                  @Query("extended") String extended);

    @Headers({"Content-Type: application/json",
            "trakt-api-version: 2"})
    @GET("movies/{id}/ratings")
    Observable<TraktMovieRating> getMovieRatings(@Header("trakt-api-key") String traktClientId,
                                                 @Path("id") String movieId);
}
