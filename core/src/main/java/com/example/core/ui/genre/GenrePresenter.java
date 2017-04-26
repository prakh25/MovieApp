package com.example.core.ui.genre;


import com.example.core.model.genre.GenreResponse;
import com.example.core.network.DataManager;
import com.example.core.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prakhar on 3/27/2017.
 */

public class GenrePresenter extends BasePresenter<GenreContract.GenreView>
        implements GenreContract.ViewActions {

    private final DataManager dataManager;

    public GenrePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getGenreList() {

        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getGenreList(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                displayList(response.body());
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                displayError(t);
            }
        });
    }

    private void displayList(GenreResponse response) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showGenreList(response.getGenres());
    }

    private void displayError(Throwable throwable) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }
}
