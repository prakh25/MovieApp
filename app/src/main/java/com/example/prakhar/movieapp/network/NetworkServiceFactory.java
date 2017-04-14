package com.example.prakhar.movieapp.network;

import com.example.prakhar.movieapp.BuildConfig;
import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.utils.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by Prakhar on 2/22/2017.
 */

public class NetworkServiceFactory {

    private static Retrofit provideRetrofit(String baseUrl) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    private static Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(Constants.MAX_AGE, TimeUnit.SECONDS)
                    .build();

            return response.newBuilder()
                    .header(Constants.CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }

    public static TmdbService provideTmdbService() {
        return provideRetrofit(Constants.TMDB_BASE_URL).create(TmdbService.class);
    }

    public static TraktService provideTraktService() {
        return provideRetrofit(Constants.TRAKT_BASE_URL).create(TraktService.class);
    }
}