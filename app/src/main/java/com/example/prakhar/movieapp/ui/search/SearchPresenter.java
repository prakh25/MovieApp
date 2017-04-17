package com.example.prakhar.movieapp.ui.search;

import com.example.prakhar.movieapp.model.search.SearchResponse;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prakhar on 3/20/2017.
 */

public class SearchPresenter extends BasePresenter<SearchContract.SearchView>
        implements SearchContract.ViewActions {

    private final DataManager dataManager;

    public SearchPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void onSearchQueryEntered(String query) {
        getSearchResult(query);
    }

    private void getSearchResult(String query) {

        if(!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getMultiSearchResponse(query,
                new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        displayResults(response.body());
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
    }

    private void displayResults(SearchResponse response) {
        if(!isViewAttached()) return;
        mView.hideProgress();
        mView.showSearch(response.getResults());
    }

    private void displayError(Throwable e) {
        if(!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(e.getMessage());
    }
}
