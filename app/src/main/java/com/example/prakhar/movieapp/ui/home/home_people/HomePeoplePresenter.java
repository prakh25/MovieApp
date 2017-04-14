package com.example.prakhar.movieapp.ui.home.home_people;

import com.example.prakhar.movieapp.model.person_search.PersonSearchResponse;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Prakhar on 4/8/2017.
 */

public class HomePeoplePresenter extends BasePresenter<HomePeopleContract.HomePeopleView>
        implements HomePeopleContract.ViewActions {

    private static final int ITEM_REQUEST_INITIAL_PAGE = 1;

    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable;

    public HomePeoplePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void onInitialisedRequest() {
        onPopularPeopleRequested(ITEM_REQUEST_INITIAL_PAGE);
    }

    @Override
    public void onListEndReached(int page) {
        onPopularPeopleRequested(page);
    }

    private void onPopularPeopleRequested(int page) {
        mView.showProgress();

        compositeDisposable.add(dataManager.getPopularPersons(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::displayPeople, this::displayError));
    }

    private void displayPeople(PersonSearchResponse response) {
        mView.hideProgress();
        if(!response.getPersonSearchResults().isEmpty()) {
            mView.showPopularPeople(response.getPersonSearchResults());
        }
    }

    private void displayError(Throwable throwable) {
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
