package com.example.prakhar.movieapp.ui.home.home_movie;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.model.tmdb.MovieResponse;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;
import com.example.prakhar.movieapp.utils.Constants;
import com.example.prakhar.movieapp.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Prakhar on 2/22/2017.
 */

public class HomeMoviePresenter extends BasePresenter<HomeMovieContract.HomeMovieView>
        implements HomeMovieContract.ViewActions {

    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
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
        mView.showProgress();

        compositeDisposable.add(dataManager.getMoviesList(region, Constants.SORT_ORDER, Constants.PAGE_NUMBER,
                Utils.minDate(Utils.getTodayDate()), Utils.getTodayDate())
                .flatMap(movieResponse -> {
                    Observable<MovieResponse> comingSoonObservable =
                            dataManager.getComingSoonMovies(region, Constants.PAGE_NUMBER);
                    return Observable.zip(comingSoonObservable, Observable.just(movieResponse),
                            Pair::new);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayResult, this::displayError));
    }

    private void displayResult(Pair moviePair) {
        MovieResponse comingSoon = (MovieResponse) moviePair.first;
        MovieResponse nowPlaying = (MovieResponse) moviePair.second;

        mView.hideProgress();

        mView.showNowPlayingMovies(nowPlaying.getResults());

        mView.showComingSoonMovies(comingSoon.getResults());

        mView.showLinks();
    }

    private void displayError(Throwable throwable) {
        mView.showError(throwable.getMessage());
    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }

}
