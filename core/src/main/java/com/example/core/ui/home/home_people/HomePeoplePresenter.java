package com.example.core.ui.home.home_people;


import com.example.core.model.person_search.PersonSearchResponse;
import com.example.core.network.DataManager;
import com.example.core.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Prakhar on 4/8/2017.
 */

public class HomePeoplePresenter extends BasePresenter<HomePeopleContract.HomePeopleView>
        implements HomePeopleContract.ViewActions {

    private static final int ITEM_REQUEST_INITIAL_PAGE = 1;

    private final DataManager dataManager;

    public HomePeoplePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
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

        if(!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getPopularPersons(page,
                new Callback<PersonSearchResponse>() {
                    @Override
                    public void onResponse(Call<PersonSearchResponse> call, Response<PersonSearchResponse> response) {
                        Timber.i("response " + response.code());
                        if(response.code() == 200) {
                            displayPeople(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonSearchResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
    }

    private void displayPeople(PersonSearchResponse response) {

        if(!isViewAttached()) return;
        mView.hideProgress();

        if(response.getPersonSearchResults().isEmpty()) {
            mView.showEmpty();
            return;
        }

        if(!response.getPersonSearchResults().isEmpty()) {
            mView.showPopularPeople(response.getPersonSearchResults());
        }
    }

    private void displayError(Throwable throwable) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }
}
