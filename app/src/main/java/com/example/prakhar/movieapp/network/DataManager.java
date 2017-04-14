package com.example.prakhar.movieapp.network;


import android.support.annotation.Nullable;

import com.example.prakhar.movieapp.BuildConfig;
import com.example.prakhar.movieapp.model.genre.GenreResponse;
import com.example.prakhar.movieapp.model.movie_detail.MovieDetail;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.TmdbMovieDetail;
import com.example.prakhar.movieapp.model.movie_detail.trakt.TraktMovieRating;
import com.example.prakhar.movieapp.model.people_detail.PeopleDetails;
import com.example.prakhar.movieapp.model.person_search.PersonSearchResponse;
import com.example.prakhar.movieapp.model.release_dates.ReleaseDateResponse;
import com.example.prakhar.movieapp.model.search.SearchResponse;
import com.example.prakhar.movieapp.model.tmdb.Images;
import com.example.prakhar.movieapp.model.tmdb.MovieResponse;
import com.example.prakhar.movieapp.model.trakt.box_office.BoxOffice;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Prakhar on 2/22/2017.
 */

public class DataManager {

    private static DataManager instance;

    private final TmdbService tmdbService;
    private final TraktService traktService;

    public static DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager() {
        tmdbService = NetworkServiceFactory.provideTmdbService();
        traktService = NetworkServiceFactory.provideTraktService();
    }

    public Observable<MovieResponse> getMoviesList(@Nullable String countryCode, String sortOrder, int page,
                                                             String minReleaseDate, String maxReleaseDate) {
        if(countryCode == null) {
            return tmdbService.getNowPlayingMovies(BuildConfig.TMDB_API_KEY, null, sortOrder,
                    Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, minReleaseDate,
                    maxReleaseDate);
        } else {
            return tmdbService.getNowPlayingMovies(BuildConfig.TMDB_API_KEY, countryCode, sortOrder,
                    Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, minReleaseDate,
                    maxReleaseDate);
        }
    }

    public Observable<MovieResponse> getPopularMovies(@Nullable String countryCode, int page) {
        if(countryCode == null) {
            return tmdbService.getPopularMovies(BuildConfig.TMDB_API_KEY, page, null);
        } else {
            return tmdbService.getPopularMovies(BuildConfig.TMDB_API_KEY, page, countryCode);
        }
    }

    public Observable<MovieResponse> getTopRatedMovies(@Nullable String countryCode, int page) {
        if(countryCode == null) {
            return tmdbService.getTopRatedMovies(BuildConfig.TMDB_API_KEY, page, null);
        } else {
            return tmdbService.getTopRatedMovies(BuildConfig.TMDB_API_KEY, page, countryCode);
        }
    }

    public Observable<List<BoxOffice>> getWeekendBoxOffice() {
        return traktService.getWeekendBoxOffice(BuildConfig.TRAKT_CLIENT_ID);
    }

    public Observable<Images> getPosterImages(Integer movieId) {
        return tmdbService.getMovieImages(movieId, BuildConfig.TMDB_API_KEY,
                Constants.TMDB_ICLUDE_IMAGE_LANGUAGE);
    }

    public Observable<TmdbMovieDetail> getMovieDetail(Integer movieId) {
        return tmdbService.getMovie(movieId, BuildConfig.TMDB_API_KEY,
                Constants.TMDB_APPEND_TO_RESPONSE);
    }

    public Observable<ReleaseDateResponse> getReleaseDates(Integer movieId) {
        return tmdbService.getReleaseDates(movieId, BuildConfig.TMDB_API_KEY);
    }

    public Observable<MovieDetail> getDetail(Integer movieId) {
        return tmdbService.getDetail(movieId, BuildConfig.TMDB_API_KEY);
    }

    public Observable<MovieResponse> getComingSoonMovies(String countryCode, int page) {
        return tmdbService.getComingSoonMovies(BuildConfig.TMDB_API_KEY, page, countryCode);
    }

    public Observable<MovieResponse> getMovieSearchResults(String query, String regionCode,
                                                       Integer page) {
        return tmdbService.getSearchResponse(BuildConfig.TMDB_API_KEY, query, page, regionCode);
    }

    public Observable<TraktMovieRating> getTraktMovieRating(String movieId) {
        return traktService.getMovieRatings(BuildConfig.TRAKT_CLIENT_ID, movieId);
    }

    public Observable<GenreResponse> getGenreList() {
        return tmdbService.getGenreList(BuildConfig.TMDB_API_KEY);
    }

    public Observable<MovieResponse> getMoviesByGenre(String region, String genreId, Integer page) {
        return tmdbService.getMovieByGenre(BuildConfig.TMDB_API_KEY, region, Constants.SORT_ORDER,
                Constants.INCLUDE_ADULT, Constants.INCLUDE_VIDEO, page, genreId);
    }

    public Observable<PeopleDetails> getPersonDetails(Integer personId) {
        return tmdbService.getPeopleDetail(personId, BuildConfig.TMDB_API_KEY,
                Constants.TMDB_PERSON_APPEND_TO_RESPONSE);
    }

    public Observable<PersonSearchResponse> getPersonKnownFor(String query) {
        return tmdbService.getPersonSearchResponse(BuildConfig.TMDB_API_KEY, query);
    }

    public Observable<SearchResponse> getMultiSearchResponse(String query) {
        return tmdbService.getSearchResult(BuildConfig.TMDB_API_KEY, query);
    }

    public Observable<PersonSearchResponse> getPopularPersons(int page) {
        return tmdbService.getPopularPerson(BuildConfig.TMDB_API_KEY, page);
    }
}
