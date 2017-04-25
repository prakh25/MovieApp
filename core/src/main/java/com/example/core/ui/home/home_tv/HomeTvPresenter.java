package com.example.core.ui.home.home_tv;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.core.MovieApp;
import com.example.core.model.home.tv.TvResponse;
import com.example.core.network.DataManager;
import com.example.core.ui.base.BasePresenter;
import com.example.core.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prakhar on 4/21/2017.
 */

public class HomeTvPresenter extends BasePresenter<HomeTvContract.HomeTvView>
        implements HomeTvContract.ViewActions {

    private static final int INITIAL_PAGE_NUMBER = 1;

    private final DataManager dataManager;
    private String region;

    public HomeTvPresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(MovieApp.getApp());
        region = countryCode.getString(Constants.COUNTRY_CODE, null);
    }

    @Override
    public void onInitialRequest() {
        getPopularTvShows(INITIAL_PAGE_NUMBER);
    }

    private void getPopularTvShows(int page) {
        if(!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();

        dataManager.getPopularTvList(page, new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                getTopRatedTvShows(response.body(), page);
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                displayError(t);
            }
        });
    }

    private void getTopRatedTvShows(TvResponse popularShows, int page) {
        dataManager.getTopRatedTvList(page, new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                Pair<TvResponse, TvResponse> pair = new Pair<>(response.body(), popularShows);
                displayResults(pair);
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                displayError(t);
            }
        });
    }

    private void displayResults(Pair pair) {

        TvResponse topRatedShoes = (TvResponse) pair.first;
        TvResponse popularShows = (TvResponse) pair.second;

        if(!isViewAttached()) return;
        mView.hideProgress();

        mView.showPopularTvShows(popularShows.getTvResults());

        mView.showTopRatedTvShows(topRatedShoes.getTvResults());
    }

    private void displayError(Throwable throwable) {
        if(!isViewAttached())return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }
}
