package com.example.core.utils;

/**
 * Created by Prakhar on 2/18/2017.
 */

public final class Constants {

    //OkHttp
//    public static final String CACHE_CONTROL = "cache_control";
    public static final int HTTP_CACHE_SIZE = 10*1024*1024; //10 MB
//    public static final int MAX_AGE = 10;
    public static final String CACHE_DIR = "http_cache";

    //TMDB
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final boolean INCLUDE_ADULT = false;
    public static final boolean INCLUDE_VIDEO = false;
    public static final String TMDB_APPEND_TO_RESPONSE =
            "recommendations,images,credits,videos,release_dates";
    public static final String TMDB_PERSON_APPEND_TO_RESPONSE =
            "combined_credits,external_ids";

    //MainActivity
    public static final String COUNTRY_CODE = "countryCode";

    //TMDB MOVIE DISCOVER
    public static final String SORT_ORDER = "popularity.desc";
    public static final int PAGE_NUMBER = 1;

    //TRAKT
    public static final String TRAKT_BASE_URL = "https://api.trakt.tv/";

    //Realm
    public static final String FIELD_MOVIE_ID = "movieId";
    public static final String FIELD_LIST_ID = "id";

    private Constants() {
    }
}
