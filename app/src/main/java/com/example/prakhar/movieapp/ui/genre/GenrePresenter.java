package com.example.prakhar.movieapp.ui.genre;

import com.example.prakhar.movieapp.model.genre.GenreResponse;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Prakhar on 3/27/2017.
 */

public class GenrePresenter extends BasePresenter<GenreContract.GenreView>
        implements GenreContract.ViewActions {

    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GenrePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
//        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getGenreList() {
        compositeDisposable.add(
                dataManager.getGenreList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::displayList, this::displayError)
        );
    }

    private void displayList(GenreResponse response) {
        Timber.i(response.getGenres().get(0).getName());
        mView.showGenreList(response.getGenres());
    }

    private void displayError(Throwable throwable) {
    }
}
