package com.example.core.ui.home.home_movie;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.example.core.MovieApp;
import com.example.core.model.home.movie.MovieResponse;
import com.example.core.network.DataManager;
import com.example.core.ui.base.BasePresenter;
import com.example.core.utils.Constants;
import com.example.core.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prakhar on 2/22/2017.
 */

public class HomeMoviePresenter extends BasePresenter<HomeMovieContract.HomeMovieView>
        implements HomeMovieContract.ViewActions {

    private final DataManager dataManager;
    private String region;

    public HomeMoviePresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(MovieApp.getApp());
        region = countryCode.getString(Constants.COUNTRY_CODE, null);
    }

    @Override
    public void onInitializeRequest() {
        movieHomePageRequested();
    }

    private void movieHomePageRequested() {

        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getMoviesList(region, Constants.SORT_ORDER, Constants.PAGE_NUMBER,
                Utils.minDate(Utils.getTodayDate()), Utils.getTodayDate(),
                new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        getComingSoonMovies(response.body());
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
    }

    private void getComingSoonMovies(MovieResponse nowPlaying) {
        dataManager.getComingSoonMovies(region, Constants.PAGE_NUMBER,
                new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        MovieResponse comingSoon = response.body();
                        Pair<MovieResponse, MovieResponse> pair =
                                new Pair<>(comingSoon, nowPlaying);
                        displayResult(pair);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
    }

    private void displayResult(Pair moviePair) {
        MovieResponse comingSoon = (MovieResponse) moviePair.first;
        MovieResponse nowPlaying = (MovieResponse) moviePair.second;

        if (!isViewAttached()) return;

        mView.hideProgress();

        mView.showNowPlayingMovies(nowPlaying.getResults());

        mView.showComingSoonMovies(comingSoon.getResults());

        mView.showLinks();
    }

    private void displayError(Throwable throwable) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }

}
