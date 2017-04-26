package com.example.core.network;


import com.example.core.BuildConfig;
import com.example.core.MovieApp;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;
import com.example.core.utils.Constants;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by Prakhar on 2/22/2017.
 */

class NetworkServiceFactory {

    private static Retrofit provideRetrofit(String baseUrl) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient provideOkhttpClient() {

        return new OkHttpClient.Builder()
                .addInterceptor(provideLoggingInterceptor())
                .cache(provideCache())
                .build();
    }

    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(MovieApp.getCache(), Constants.CACHE_DIR),
                    Constants.HTTP_CACHE_SIZE);
        } catch (Exception e) {
            Timber.e(e, "Could not create cache");
        }

        return cache;
    }

    private static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(message ->
                        Timber.d(message)
                );

        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
        return httpLoggingInterceptor;
    }

    public static TmdbService provideTmdbService() {
        return provideRetrofit(Constants.TMDB_BASE_URL).create(TmdbService.class);
    }

    public static TraktService provideTraktService() {
        return provideRetrofit(Constants.TRAKT_BASE_URL).create(TraktService.class);
    }
}
