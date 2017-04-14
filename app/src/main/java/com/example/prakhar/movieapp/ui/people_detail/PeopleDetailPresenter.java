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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Prakhar on 4/3/2017.
 */

public class PeopleDetailPresenter extends BasePresenter<PeopleDetailContract.PeopleDetailView>
        implements PeopleDetailContract.ViewActions {

    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable;
    private List<Cast> castList;
    private List<Crew> crewList;

    public PeopleDetailPresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        compositeDisposable = new CompositeDisposable();
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
    }

    @Override
    public void onPersonDetailRequested(Integer personId) {
        getPersonDetails(personId);
    }

    private void getPersonDetails(Integer personId) {
        mView.showProgress();

        compositeDisposable.add(dataManager.getPersonDetails(personId)
                .flatMap(details -> {
                    Observable<PersonSearchResult> responseObservable =
                            dataManager.getPersonKnownFor(details.getName())
                                    .map(PersonSearchResponse::getPersonSearchResults)
                                    .flatMap(Observable::fromIterable)
                                    .filter(personSearchResult -> personSearchResult.getId().equals(personId));

                    return Observable.zip(responseObservable, Observable.just(details), Pair::new);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(personSearchResultPeopleDetailsPair -> {
                    PeopleDetails details = personSearchResultPeopleDetailsPair.second;
                    mView.showPeopleDetailHeader(details.getName(), details.getExternalIds());
                })
                .delay(2L, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayPersonDetails, this::onError)
        );
    }

    private void displayPersonDetails(Pair personPair) {
        mView.hideProgress();

        PersonSearchResult result = (PersonSearchResult) personPair.first;
        PeopleDetails details = (PeopleDetails) personPair.second;

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
        compositeDisposable.dispose();
    }
}
