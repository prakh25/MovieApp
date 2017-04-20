package com.example.prakhar.movieapp.ui.people_detail;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.example.prakhar.movieapp.model.people_detail.Cast;
import com.example.prakhar.movieapp.model.people_detail.CastReleaseDateComparator;
import com.example.prakhar.movieapp.model.people_detail.Crew;
import com.example.prakhar.movieapp.model.people_detail.CrewReleaseDateComparator;
import com.example.prakhar.movieapp.model.people_detail.PeopleDetails;
import com.example.prakhar.movieapp.model.person_search.PersonSearchResponse;
import com.example.prakhar.movieapp.model.person_search.PersonSearchResult;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prakhar on 4/3/2017.
 */

class PeopleDetailPresenter extends BasePresenter<PeopleDetailContract.PeopleDetailView>
        implements PeopleDetailContract.ViewActions {

    private final DataManager dataManager;
    private List<Cast> castList;
    private List<Crew> crewList;

    PeopleDetailPresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
    }

    @Override
    public void onPersonDetailRequested(Integer personId) {
        getPersonDetails(personId);
    }

    private void getPersonDetails(Integer personId) {
        if(!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();

        dataManager.getPersonDetails(personId,
                new Callback<PeopleDetails>() {
                    @Override
                    public void onResponse(Call<PeopleDetails> call, Response<PeopleDetails> response) {
                        findPersonKnownForCredits(response.body());
                    }

                    @Override
                    public void onFailure(Call<PeopleDetails> call, Throwable t) {
                        onError(t);
                    }
                });
    }

    private void findPersonKnownForCredits(PeopleDetails details) {
        dataManager.getPersonKnownFor(details.getName(),
                new Callback<PersonSearchResponse>() {
                    @Override
                    public void onResponse(Call<PersonSearchResponse> call, Response<PersonSearchResponse> response) {
                        PersonSearchResult personSearchResult = new PersonSearchResult();
                        for(PersonSearchResult result : response.body().getPersonSearchResults()) {
                            if(result.getId().equals(details.getId())) {
                                personSearchResult = result;
                            }
                        }
                        Pair<PersonSearchResult, PeopleDetails> pair =
                                new Pair<>(personSearchResult, details);
                        displayPersonDetails(pair);
                    }

                    @Override
                    public void onFailure(Call<PersonSearchResponse> call, Throwable t) {
                        onError(t);
                    }
                });
    }

    private void displayPersonDetails(Pair personPair) {

        if(!isViewAttached()) return;
        mView.hideProgress();

        PersonSearchResult result = (PersonSearchResult) personPair.first;
        PeopleDetails details = (PeopleDetails) personPair.second;

        mView.showPeopleDetailHeader(details.getName(), details.getExternalIds());

        if (details.getBiography() != null && !details.getBiography().isEmpty()) {
            mView.showPersonBio(details.getBiography());
        }

        if (!result.getKnownFor().isEmpty()) {
            mView.showPersonKnownFor(result.getKnownFor());
        }

        if (details.getGender() != null || !details.getBirthday().isEmpty()) {
            mView.showPersonalInfo(details.getGender(), details.getBirthday(), details.getDeathday(),
                    details.getPlaceOfBirth(), details.getAlsoKnownAs());
        }

        mView.showExternalLinks(details.getHomepage(), details.getId(), details.getName());

        castList.addAll(details.getCombinedCredits().getCast());


        crewList.addAll(details.getCombinedCredits().getCrew());
    }

    private void onError(Throwable throwable) {
        if(!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }

    public List<Cast> getCastList() {
        Collections.sort(castList, new CastReleaseDateComparator());
        return castList;
    }

    public List<Crew> getCrewList() {
        Collections.sort(crewList, new CrewReleaseDateComparator());
        return crewList;
    }

    public void onDestroy() {
        castList.clear();
    }
}
