package com.example.core.network;


import android.support.annotation.Nullable;

import com.example.core.model.genre.GenreResponse;
import com.example.core.model.home.movie.MovieResponse;
import com.example.core.model.home.tv.TvResponse;
import com.example.core.model.more_movie_list.box_office.BoxOffice;
import com.example.core.model.movie_detail.tmdb.TmdbMovieDetail;
import com.example.core.model.movie_detail.trakt.TraktMovieRating;
import com.example.core.model.people_detail.PeopleDetails;
import com.example.core.model.person_search.PersonSearchResponse;
import com.example.core.model.search.SearchResponse;

import java.util.List;

import retrofit2.Callback;
import com.example.core.utils.Constants;

import static com.example.core.BuildConfig.TMDB_API_KEY;
import static com.example.core.BuildConfig.TRAKT_CLIENT_ID;


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
            tmdbService.getNowPlayingMovies(TMDB_API_KEY, null, sortOrder,
                    Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, minReleaseDate,
                    maxReleaseDate).enqueue(responseCallback);
        } else {
            tmdbService.getNowPlayingMovies(TMDB_API_KEY, countryCode, sortOrder,
                    Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, minReleaseDate,
                    maxReleaseDate).enqueue(responseCallback);
        }
    }

    public void getPopularMovies(@Nullable String countryCode, int page,
                                 Callback<MovieResponse> callback) {
        if (countryCode == null) {
            tmdbService.getPopularMovies(TMDB_API_KEY, page, null)
                    .enqueue(callback);
        } else {
            tmdbService.getPopularMovies(TMDB_API_KEY, page, countryCode)
                    .enqueue(callback);
        }
    }

    public void getTopRatedMovies(@Nullable String countryCode, int page,
                                  Callback<MovieResponse> callback) {
        if (countryCode == null) {
            tmdbService.getTopRatedMovies(TMDB_API_KEY, page, null)
                    .enqueue(callback);
        } else {
            tmdbService.getTopRatedMovies(TMDB_API_KEY, page, countryCode)
                    .enqueue(callback);
        }
    }

    public void getWeekendBoxOffice(Callback<List<BoxOffice>> callback) {
        traktService.getWeekendBoxOffice(TRAKT_CLIENT_ID).enqueue(callback);
    }

//    public Observable<Images> getPosterImages(Integer movieId) {
//        return tmdbService.getMovieImages(movieId, BuildConfig.TMDB_API_KEY,
//                Constants.TMDB_ICLUDE_IMAGE_LANGUAGE);
//    }

    public void getMovieDetail(Integer movieId, Callback<TmdbMovieDetail> callback) {
        tmdbService.getMovie(movieId, TMDB_API_KEY,
                Constants.TMDB_APPEND_TO_RESPONSE).enqueue(callback);
    }

//    public void getReleaseDates(Integer movieId, Callback<ReleaseDateResponse> callback) {
//        tmdbService.getReleaseDates(movieId, BuildConfig.TMDB_API_KEY)
//                .enqueue(callback);
//    }

    public void getBoxOfficeMovieDetail(Integer movieId, Callback<TmdbMovieDetail> callback) {
        tmdbService.getDetail(movieId, TMDB_API_KEY)
                .enqueue(callback);
    }

    public void getComingSoonMovies(String countryCode, int page,
                                    Callback<MovieResponse> callback) {
        tmdbService.getComingSoonMovies(TMDB_API_KEY, page, countryCode)
                .enqueue(callback);
    }

//    public void getMovieSearchResults(String query, String regionCode,
//                                      Integer page, Callback<MovieResponse> callback) {
//        tmdbService.getSearchResponse(BuildConfig.TMDB_API_KEY, query, page, regionCode)
//                .enqueue(callback);
//    }

    public void getTraktMovieRating(String movieId,
                                    Callback<TraktMovieRating> callback) {
        traktService.getMovieRatings(TRAKT_CLIENT_ID, movieId)
                .enqueue(callback);
    }

    public void getGenreList(Callback<GenreResponse> callback) {
        tmdbService.getGenreList(TMDB_API_KEY).enqueue(callback);
    }

    public void getMoviesByGenre(String region, String genreId, Integer page,
                                 Callback<MovieResponse> callback) {
        tmdbService.getMovieByGenre(TMDB_API_KEY, region, Constants.SORT_ORDER,
                Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, genreId)
                .enqueue(callback);
    }

    public void getPopularTvList(int page, Callback<TvResponse> callback) {
        tmdbService.getPopularTvShows(TMDB_API_KEY, page)
                .enqueue(callback);
    }

    public void getTopRatedTvList(int page, Callback<TvResponse> callback) {
        tmdbService.getTopRatedTvShows(TMDB_API_KEY, page)
                .enqueue(callback);
    }

    public void getPersonDetails(Integer personId, Callback<PeopleDetails> callback) {
        tmdbService.getPeopleDetail(personId, TMDB_API_KEY,
                Constants.TMDB_PERSON_APPEND_TO_RESPONSE).enqueue(callback);
    }

    public void getPersonKnownFor(String query,
                                  Callback<PersonSearchResponse> callback) {
        tmdbService.getPersonSearchResponse(TMDB_API_KEY, query)
                .enqueue(callback);
    }

    public void getMultiSearchResponse(String query,
                                       Callback<SearchResponse> callback) {
        tmdbService.getSearchResult(TMDB_API_KEY, query)
                .enqueue(callback);
    }

    public void getPopularPersons(int page, Callback<PersonSearchResponse> callback) {
        tmdbService.getPopularPerson(TMDB_API_KEY, page).enqueue(callback);
    }
}
