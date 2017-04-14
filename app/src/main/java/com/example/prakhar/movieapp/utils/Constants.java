package com.example.prakhar.movieapp.utils;

/**
 * Created by Prakhar on 2/18/2017.
 */

public final class Constants {

    private Constants() {
    }

    //OkHttp
    public static final String CACHE_CONTROL = "cache_control";
    public static final int HTTP_CACHE_SIZE = 10*1024*1024; //10 MB
    public static final int MAX_AGE = 10;
    public static final int MAX_STALE_DAYS  = 1;
    public static final String CACHE_DIR = "http_cache";

    //TMDB
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final boolean INCLUDE_ADULT = false;
    public static final boolean INCLUDE_VIDEO = false;
    public static final String TMDB_ICLUDE_IMAGE_LANGUAGE = "en,null";
    public static final String TMDB_APPEND_TO_RESPONSE =
            "recommendations,images,credits,videos,release_dates";
    public static final String TMDB_PERSON_APPEND_TO_RESPONSE =
            "combined_credits,external_ids";

    //MainActivity
    public static final String COUNTRY_CODE = "countryCode";

    //TMDB MOVIE DISCOVER
    public static final String SORT_ORDER = "popularity.desc";
    public static final String RELEASE_SORT_ORDER = "primary_release_date.asc";
    public static final int PAGE_NUMBER = 1;

    //TRAKT
    public static final String TRAKT_BASE_URL = "https://api.trakt.tv/";
    public static final int TRAKT_API_VERSION = 2;
    public static final String TRAKT_CONTENT_TYPE = "application/json";
    public static final String SUMMARY_EXTENDED = "full";

    //Omdb
    public static final String OMDB_BASE_URL = "http://www.omdbapi.com/";
    public static final String OMDB_PLOT_TYPE = "full";
    public static final String OMDB_RETURN_DATA_TYPE = "json";
    public static final boolean OMDB_INCLUDE_ROTTEN_TOMATOES = true;

    public static final String TRANSACTION_PREFIX_ = "transaction_prefix_";

    //Detail More Activity
    public static final String ARG_TOOLBAR_TITLE = "argTitle";
    public static final String ARG_TOOLBAR_COLOR = "argColor";

    //IMDB External Links
    public static final String HTTP_IMDB_COM_TITLE = "http://www.imdb.com/title/";

    //TMDB External Links
    public static final String TMDB_MOVIE_LINK = "https://www.themoviedb.org/movie/";
    public static final String TMDB_PERSON_LINK = "https://www.themoviedb.org/person/";
    public static final String GO0GLE_SEARCH_LINK = "http://www.google.com/#q=";
}
