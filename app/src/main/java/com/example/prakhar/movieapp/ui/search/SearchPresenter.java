package com.example.prakhar.movieapp.ui.search;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.model.search.SearchResponse;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;
import com.example.prakhar.movieapp.utils.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Prakhar on 3/20/2017.
 */

public class SearchPresenter extends BasePresenter<SearchContract.SearchView>
        implements SearchContract.ViewActions {

    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String region;

    public SearchPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(MovieApp.getApp());
        region = countryCode.getString(Constants.COUNTRY_CODE, null);
    }


    @Override
    public void onSearchQueryEntered(String query) {
        getSearchResult(query);
    }

    private void getSearchResult(String query) {
        compositeDisposable.add(
                dataManager.getMultiSearchResponse(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::displayResults, this::displayError)
        );
    }

    private void displayResults(SearchResponse response) {
        mView.showSearch(response.getResults());
    }

    private void displayError(Throwable e) {
        mView.showError(e.getMessage());
    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
