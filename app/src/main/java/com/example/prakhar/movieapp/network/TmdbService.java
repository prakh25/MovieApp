package com.example.prakhar.movieapp.network;

import android.support.annotation.NonNull;

import com.example.prakhar.movieapp.model.genre.GenreResponse;
import com.example.prakhar.movieapp.model.movie_detail.MovieDetail;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.TmdbMovieDetail;
import com.example.prakhar.movieapp.model.people_detail.PeopleDetails;
import com.example.prakhar.movieapp.model.person_search.PersonSearchResponse;
import com.example.prakhar.movieapp.model.release_dates.ReleaseDateResponse;
import com.example.prakhar.movieapp.model.search.SearchResponse;
import com.example.prakhar.movieapp.model.tmdb.Images;
import com.example.prakhar.movieapp.model.tmdb.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Prakhar on 2/24/2017.
 */

public interface TmdbService {

    @GET("discover/movie")
    Call<MovieResponse> getNowPlayingMovies(@NonNull @Query("api_key") String apiKey,
                                            @Query("region") String countryCode,
                                            @Query("sort_by") String sortOrder,
                                            @Query("include_adult") boolean includeAdult,
                                            @Query("include_video") boolean includeVideo,
                                            @Query("page") int currentPage,
                                            @Query("release_date.gte") String minReleaseDate,
                                            @Query("release_date.lte") String maxReleaseDate);

    @GET("movie/{movie_id}/images")
    Call<Images> getMovieImages(@Path("movie_id") Integer movieId,
                                      @Query("api_key") String apiKey,
                                      @Query("include_image_language") String imageLanguage);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@NonNull @Query("api_key") String apiKey,
                                             @Query("page") int currentPage,
                                             @Query("region") String countryCode);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@NonNull @Query("api_key") String apiKey,
                                                @Query("page") int currentPage,
                                                @Query("region") String countryCode);

    @GET("movie/upcoming")
    Call<MovieResponse> getComingSoonMovies(@NonNull @Query("api_key") String apiKey,
                                               @Query("page") int currentPage,
                                               @Query("region") String countryCode);

    @GET("movie/{movie_id}")
    Call<TmdbMovieDetail> getMovie(@Path("movie_id") Integer movieId,
                                         @Query("api_key") String apiKey,
                                         @Query("append_to_response") String appendToResponse);

    @GET("movie/{movie_id}/release_dates")
    Call<ReleaseDateResponse> getReleaseDates(@Path("movie_id") Integer movieId,
                                                    @NonNull @Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getDetail(@Path("movie_id") Integer movieId,
                                     @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> getSearchResponse(@Query("api_key") String apiKey,
                                                 @Query("query") String searchQuery,
                                                 @Query("page") Integer currentPage,
                                                 @Query("region") String regionCode);

    @GET("search/multi")
    Call<SearchResponse> getSearchResult(@Query("api_key") String apiKey,
                                               @Query("query") String searchQuery);

    @GET("genre/movie/list")
    Call<GenreResponse> getGenreList(@Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<MovieResponse> getMovieByGenre(@NonNull @Query("api_key") String apiKey,
                                                  @Query("region") String countryCode,
                                                  @Query("sort_by") String sortOrder,
                                                  @Query("include_adult") boolean includeAdult,
                                                  @Query("include_video") boolean includeVideo,
                                                  @Query("page") int currentPage,
                                                  @Query("with_genres") String genre);

    @GET("person/{person_id}")
    Call<PeopleDetails> getPeopleDetail(@Path("person_id") Integer personId,
                                              @Query("api_key") String apiKey,
                                              @Query("append_to_response") String appendToResponse);

    @GET("search/person")
    Call<PersonSearchResponse> getPersonSearchResponse(@Query("api_key") String apiKey,
                                                             @Query("query") String searchQuery);

    @GET("person/popular")
    Call<PersonSearchResponse> getPopularPerson(@Query("api_key") String apiKey,
                                                      @Query("page") int page);
}
