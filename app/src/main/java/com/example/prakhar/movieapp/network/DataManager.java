package com.example.prakhar.movieapp.network;


import android.support.annotation.Nullable;

import com.example.prakhar.movieapp.BuildConfig;
import com.example.prakhar.movieapp.model.genre.GenreResponse;
import com.example.prakhar.movieapp.model.movie_detail.MovieDetail;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.TmdbMovieDetail;
import com.example.prakhar.movieapp.model.movie_detail.trakt.TraktMovieRating;
import com.example.prakhar.movieapp.model.people_detail.PeopleDetails;
import com.example.prakhar.movieapp.model.person_search.PersonSearchResponse;
import com.example.prakhar.movieapp.model.search.SearchResponse;
import com.example.prakhar.movieapp.model.tmdb.MovieResponse;
import com.example.prakhar.movieapp.model.trakt.box_office.BoxOffice;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.List;

import retrofit2.Callback;


/**
 * Created by Prakhar on 2/22/2017.
 */

public class DataManager {

    private static DataManager instance;

    private final TmdbService tmdbService;
    private final TraktService traktService;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager() {
        tmdbService = NetworkServiceFactory.provideTmdbService();
        traktService = NetworkServiceFactory.provideTraktService();
    }

    public void getMoviesList(@Nullable String countryCode, String sortOrder, int page,
                              String minReleaseDate, String maxReleaseDate,
                              Callback<MovieResponse> responseCallback) {
        if (countryCode == null) {
            tmdbService.getNowPlayingMovies(BuildConfig.TMDB_API_KEY, null, sortOrder,
                    Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, minReleaseDate,
                    maxReleaseDate).enqueue(responseCallback);
        } else {
            tmdbService.getNowPlayingMovies(BuildConfig.TMDB_API_KEY, countryCode, sortOrder,
                    Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, minReleaseDate,
                    maxReleaseDate).enqueue(responseCallback);
        }
    }

    public void getPopularMovies(@Nullable String countryCode, int page,
                                 Callback<MovieResponse> callback) {
        if (countryCode == null) {
            tmdbService.getPopularMovies(BuildConfig.TMDB_API_KEY, page, null)
                    .enqueue(callback);
        } else {
            tmdbService.getPopularMovies(BuildConfig.TMDB_API_KEY, page, countryCode)
                    .enqueue(callback);
        }
    }

    public void getTopRatedMovies(@Nullable String countryCode, int page,
                                  Callback<MovieResponse> callback) {
        if (countryCode == null) {
            tmdbService.getTopRatedMovies(BuildConfig.TMDB_API_KEY, page, null)
                    .enqueue(callback);
        } else {
            tmdbService.getTopRatedMovies(BuildConfig.TMDB_API_KEY, page, countryCode)
                    .enqueue(callback);
        }
    }

    public void getWeekendBoxOffice(Callback<List<BoxOffice>> callback) {
        traktService.getWeekendBoxOffice(BuildConfig.TRAKT_CLIENT_ID).enqueue(callback);
    }

//    public Observable<Images> getPosterImages(Integer movieId) {
//        return tmdbService.getMovieImages(movieId, BuildConfig.TMDB_API_KEY,
//                Constants.TMDB_ICLUDE_IMAGE_LANGUAGE);
//    }

    public void getMovieDetail(Integer movieId, Callback<TmdbMovieDetail> callback) {
        tmdbService.getMovie(movieId, BuildConfig.TMDB_API_KEY,
                Constants.TMDB_APPEND_TO_RESPONSE).enqueue(callback);
    }

//    public void getReleaseDates(Integer movieId, Callback<ReleaseDateResponse> callback) {
//        tmdbService.getReleaseDates(movieId, BuildConfig.TMDB_API_KEY)
//                .enqueue(callback);
//    }

    public void getBoxOfficeMovieDetail(Integer movieId, Callback<MovieDetail> callback) {
        tmdbService.getDetail(movieId, BuildConfig.TMDB_API_KEY)
                .enqueue(callback);
    }

    public void getComingSoonMovies(String countryCode, int page,
                                    Callback<MovieResponse> callback) {
        tmdbService.getComingSoonMovies(BuildConfig.TMDB_API_KEY, page, countryCode)
                .enqueue(callback);
    }

//    public void getMovieSearchResults(String query, String regionCode,
//                                      Integer page, Callback<MovieResponse> callback) {
//        tmdbService.getSearchResponse(BuildConfig.TMDB_API_KEY, query, page, regionCode)
//                .enqueue(callback);
//    }

    public void getTraktMovieRating(String movieId,
                                    Callback<TraktMovieRating> callback) {
        traktService.getMovieRatings(BuildConfig.TRAKT_CLIENT_ID, movieId)
                .enqueue(callback);
    }

    public void getGenreList(Callback<GenreResponse> callback) {
        tmdbService.getGenreList(BuildConfig.TMDB_API_KEY).enqueue(callback);
    }

    public void getMoviesByGenre(String region, String genreId, Integer page,
                                 Callback<MovieResponse> callback) {
        tmdbService.getMovieByGenre(BuildConfig.TMDB_API_KEY, region, Constants.SORT_ORDER,
                Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, genreId)
                .enqueue(callback);
    }

    public void getPersonDetails(Integer personId, Callback<PeopleDetails> callback) {
        tmdbService.getPeopleDetail(personId, BuildConfig.TMDB_API_KEY,
                Constants.TMDB_PERSON_APPEND_TO_RESPONSE).enqueue(callback);
    }

    public void getPersonKnownFor(String query,
                                  Callback<PersonSearchResponse> callback) {
        tmdbService.getPersonSearchResponse(BuildConfig.TMDB_API_KEY, query)
                .enqueue(callback);
    }

    public void getMultiSearchResponse(String query,
                                       Callback<SearchResponse> callback) {
        tmdbService.getSearchResult(BuildConfig.TMDB_API_KEY, query)
                .enqueue(callback);
    }

    public void getPopularPersons(int page, Callback<PersonSearchResponse> callback) {
        tmdbService.getPopularPerson(BuildConfig.TMDB_API_KEY, page).enqueue(callback);
    }
}
