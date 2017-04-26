package com.example.core.network;


import com.example.core.model.more_movie_list.box_office.BoxOffice;
import com.example.core.model.movie_detail.trakt.TraktMovieRating;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Prakhar on 2/28/2017.
 */

interface TraktService {

    @Headers({"Content-Type: application/json",
            "trakt-api-version: 2"})
    @GET("movies/boxoffice")
    Call<List<BoxOffice>> getWeekendBoxOffice(@Header("trakt-api-key") String traktClientId);

//    @Headers({"Content-Type: application/json",
//            "trakt-api-version: 2"})
//    @GET("movies/{id}")
//    Call<TraktMovieSummary> getMovieSummary(@Header("trakt-api-key") String traktClientId,
//                                                  @Path("id") String movieId,
//                                                  @Query("extended") String extended);

    @Headers({"Content-Type: application/json",
            "trakt-api-version: 2"})
    @GET("movies/{id}/ratings")
    Call<TraktMovieRating> getMovieRatings(@Header("trakt-api-key") String traktClientId,
                                           @Path("id") String movieId);
}
