package com.example.prakhar.movieapp.ui.genre;

import com.example.prakhar.movieapp.model.genre.GenreResponse;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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
        mView.showGenreList(response.getGenres());
    }

    private void displayError(Throwable throwable) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }
}
