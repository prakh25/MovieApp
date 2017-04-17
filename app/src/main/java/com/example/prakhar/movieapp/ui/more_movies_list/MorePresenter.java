package com.example.prakhar.movieapp.ui.more_movies_list;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.model.movie_detail.MovieDetail;
import com.example.prakhar.movieapp.model.more.MoreListResult;
import com.example.prakhar.movieapp.model.realm.MovieStatus;
import com.example.prakhar.movieapp.model.realm.UserRating;
import com.example.prakhar.movieapp.model.tmdb.MovieResponse;
import com.example.prakhar.movieapp.model.tmdb.Result;
import com.example.prakhar.movieapp.model.trakt.box_office.BoxOffice;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by Prakhar on 3/14/2017.
 */

public class MorePresenter extends BasePresenter<MoreContract.MoreView>
        implements MoreContract.ViewActions {

    private final DataManager dataManager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String region;
    private Realm realm;
    private Integer genreId;

    private static final int ITEM_REQUEST_INITIAL_PAGE = 1;

    public MorePresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(MovieApp.getApp());
        region = countryCode.getString(Constants.COUNTRY_CODE, null);
    }

    @Override
    public void mostPopularListRequested() {
        getPoularMovies(ITEM_REQUEST_INITIAL_PAGE);
    }

    @Override
    public void onTopRatedMoviesRequested() {
        getTopRatedMovies(ITEM_REQUEST_INITIAL_PAGE);
    }

    @Override
    public void onBoxOfficeRequested() {
        getBoxOfficeList();
    }

    @Override
    public void onPopularMovieByGenreRequested(Integer genreId) {
        this.genreId = genreId;
        getMoviesByGenre(ITEM_REQUEST_INITIAL_PAGE, genreId);
    }

    @Override
    public void onListEndReached(int pageNo, int id) {
        switch (id) {
            case 1:
                getPoularMovies(pageNo);
                break;
            case 2:
                getTopRatedMovies(pageNo);
                break;
            case 3:
                mView.hideProgress();
                break;
            case 4:
                getMoviesByGenre(pageNo, genreId);
                break;
        }
    }

    private void getPoularMovies(int page) {

        mView.showProgress();

        compositeDisposable.add(dataManager.getPopularMovies(region, page)
                .doOnNext(__ -> mView.showProgress())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::getMoreList)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> mView.hideProgress())
                .subscribe(this::displayResults, this::displayError)
        );
    }

    private void getTopRatedMovies(int page) {

        mView.showProgress();

        compositeDisposable.add(dataManager.getTopRatedMovies(region, page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::getMoreList)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> mView.hideProgress())
                .subscribe(this::displayResults, this::displayError)
        );
    }

    private void getMoviesByGenre(Integer page, Integer genreId) {

        mView.showProgress();

        compositeDisposable.add(dataManager.getMoviesByGenre(region, String.valueOf(genreId), page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::getMoreList)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(__ -> mView.hideProgress())
                .subscribe(this::displayResults, this::displayError)
        );
    }

    private List<MoreListResult> getMoreList(MovieResponse response) {
        List<Result> results = response.getResults();
        List<MoreListResult> moreListResults = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        for (Result result : results) {
            MoreListResult moreListResult = new MoreListResult();
            MovieStatus status = findInRealmMovieStatus(realm, result.getId());
            if (status == null) {
                Timber.i("Null status");
                moreListResult.setResult(result);
                moreListResult.setAddedToWatchlist(false);
                moreListResult.setMarkedAsFavorite(false);
                moreListResult.setUserRating(0);
            } else {
                if (!status.isRated()) {
                    moreListResult.setResult(result);
                    moreListResult.setAddedToWatchlist(status.isAddedToWatchList());
                    moreListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
                    moreListResult.setUserRating(0);
                } else {
                    int rating = findInRealmUserRating(realm, result.getId()).getUserRating();
                    moreListResult.setResult(result);
                    moreListResult.setAddedToWatchlist(status.isAddedToWatchList());
                    moreListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
                    moreListResult.setUserRating(rating);
                }
            }
            moreListResults.add(moreListResult);
        }
        realm.close();
        return moreListResults;
    }

    private void getBoxOfficeList() {

        mView.showProgress();

        compositeDisposable.add(
                dataManager.getWeekendBoxOffice()
                        .flatMap(Observable::fromIterable)
                        .flatMap(boxOffice -> {
                            Observable<MovieDetail> detailObservable =
                                    dataManager.getBoxOfficeMovieDetail(boxOffice.getMovie().getIds().getTmdb());
                            return Observable.zip(detailObservable, Observable.just(boxOffice), Pair::new);
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .map(movieDetailBoxOfficePair -> {

                            MovieDetail movieDetail = movieDetailBoxOfficePair.first;
                            BoxOffice boxOffice = movieDetailBoxOfficePair.second;
                            List<MoreListResult> boxOfficeWrappers = new ArrayList<>();
                            realm = Realm.getDefaultInstance();
                            MoreListResult wrapper = new MoreListResult();
                            MovieStatus status = findInRealmMovieStatus(realm, movieDetail.getId());
                            if (status == null) {
                                Result result = new Result();
                                result.setTitle(movieDetail.getTitle());
                                result.setId(movieDetail.getId());
                                result.setPosterPath(movieDetail.getPosterPath());
                                result.setBackdropPath(movieDetail.getBackdropPath());
                                result.setReleaseDate(movieDetail.getReleaseDate());
                                result.setVoteAverage(movieDetail.getVoteAverage());
                                wrapper.setResult(result);
                                wrapper.setRevenue(boxOffice.getRevenue());
                                wrapper.setAddedToWatchlist(false);
                                wrapper.setMarkedAsFavorite(false);
                            } else {
                                Result result = new Result();
                                result.setTitle(movieDetail.getTitle());
                                result.setId(movieDetail.getId());
                                result.setPosterPath(movieDetail.getPosterPath());
                                result.setBackdropPath(movieDetail.getBackdropPath());
                                result.setReleaseDate(movieDetail.getReleaseDate());
                                result.setVoteAverage(movieDetail.getVoteAverage());
                                wrapper.setResult(result);
                                wrapper.setRevenue(boxOffice.getRevenue());
                                wrapper.setAddedToWatchlist(status.isAddedToWatchList());
                                wrapper.setMarkedAsFavorite(status.isMarkedAsFavorite());
                            }
                            realm.close();
                            boxOfficeWrappers.add(wrapper);
                            return boxOfficeWrappers;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnEach(__ -> mView.hideProgress())
                        .subscribe(this::displayResults, this::displayError)

        );
    }

//    private void getComingSoonMovies(int page) {
//
//        compositeDisposable.add(dataManager.getMoviesList(region, Constants.SORT_ORDER, page,
//                Utils.getTomorrowDate(), Utils.comingSoonDate(Utils.getTomorrowDate()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(__ -> mView.showProgress())
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .map(movieResponse -> {
//                    List<PersonSearchResult> results = movieResponse.getPersonSearchResults();
//                    List<MoreListResult> moreListResults = new ArrayList<>();
//                    realm = Realm.getDefaultInstance();
//                    for (PersonSearchResult result : results) {
//                        MoreListResult moreListResult = new MoreListResult();
//                        MovieStatus status = realm.where(MovieStatus.class).equalTo("movieId", result.getId()).findFirst();
//                        if (status == null) {
//                            moreListResult.setMovieId(result.getId());
//                            moreListResult.setPosterPath(result.getPosterPath());
//                            moreListResult.setReleaseDate(result.getReleaseDate());
//                            moreListResult.setTitle(result.getTitle());
//                            moreListResult.setTmdbRating(result.getVoteAverage());
//                            moreListResult.setRevenue(0.0);
//                            moreListResult.setAddedToWatchlist(false);
//                            moreListResult.setMarkedAsFavorite(false);
//                        } else {
//                            moreListResult.setMovieId(result.getId());
//                            moreListResult.setPosterPath(result.getPosterPath());
//                            moreListResult.setReleaseDate(result.getReleaseDate());
//                            moreListResult.setTitle(result.getTitle());
//                            moreListResult.setTmdbRating(result.getVoteAverage());
//                            moreListResult.setRevenue(0.0);
//                            moreListResult.setAddedToWatchlist(status.isAddedToWatchList());
//                            moreListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
//                        }
//                        moreListResults.add(moreListResult);
//                    }
//                    realm.close();
//                    return moreListResults;
//                })
//                .map(moreListResults -> {
//                    for (MoreListResult moreListResult : moreListResults) {
//                        compositeDisposable.add(dataManager.getReleaseDates(moreListResult.getMovieId())
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(Schedulers.computation())
//                                .doOnNext(releaseDates -> {
//                                    for (ReleaseDatesResult releaseDatesResult : releaseDates.getPersonSearchResults()) {
//                                        if (releaseDatesResult.getIso31661().equalsIgnoreCase(region)) {
//                                            moreListResult.setRegionReleaseDate(releaseDatesResult.getReleaseDates()
//                                                    .get(0).getReleaseDate());
//                                        } else {
//                                            moreListResult.setRegionReleaseDate(null);
//                                        }
//                                    }
//                                })
//                                .subscribe());
//                    }
//                    return moreListResults;
//                })
//                .delay(2, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(moreListResults -> {
//                    mView.hideProgress();
//                    mView.showMoviesList(moreListResults);
//                })
//                .doOnError(throwable -> {
//                    mView.hideProgress();
//                    mView.showError(throwable.getMessage());
//                })
//                .subscribe()
//        );
//    }

//    private void getComingSoonMovies(int page) {
//
//        compositeDisposable.add(dataManager.getMoviesList(region, Constants.SORT_ORDER, page,
//                Utils.getTomorrowDate(), Utils.comingSoonDate(Utils.getTomorrowDate()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(__ -> mView.showProgress())
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .map(movieResponse -> Observable.just(movieResponse.getPersonSearchResults()))
//                .flatMap(listObservable -> listObservable.flatMap(Observable::fromIterable))
//                .flatMap(result -> {
//                    Observable<ReleaseDatesResult> datesResultObservable =
//                            dataManager.getReleaseDates(result.getId())
//                                    .flatMap(releaseDates -> Observable.fromIterable(releaseDates.getPersonSearchResults()))
//                                    .filter(releaseDatesResult -> releaseDatesResult.getIso31661().equalsIgnoreCase(region));
//                    return Observable.zip(datesResultObservable, Observable.just(result), Pair::new);
//                })
//                .observeOn(Schedulers.computation())
//                .map(releaseDatesResultResultPair -> {
//                    List<MoreListResult> moreListResults = new ArrayList<>();
//                    ReleaseDatesResult releaseDatesResult = releaseDatesResultResultPair.first;
//                    PersonSearchResult result = releaseDatesResultResultPair.second;
//                    realm = Realm.getDefaultInstance();
//                    MoreListResult moreListResult = new MoreListResult();
//                    MovieStatus status = realm.where(MovieStatus.class).equalTo("movieId", result.getId()).findFirst();
//                    if (status == null) {
//                        moreListResult.setMovieId(result.getId());
//                        moreListResult.setPosterPath(result.getPosterPath());
//                        moreListResult.setReleaseDate(result.getReleaseDate());
//                        moreListResult.setTitle(result.getTitle());
//                        moreListResult.setTmdbRating(result.getVoteAverage());
//                        moreListResult.setRevenue(0.0);
//                        moreListResult.setAddedToWatchlist(false);
//                        moreListResult.setMarkedAsFavorite(false);
//                        moreListResult.setRegionReleaseDate(releaseDatesResult.getReleaseDates()
//                                .get(0).getReleaseDate());
//                    } else {
//                        moreListResult.setMovieId(result.getId());
//                        moreListResult.setPosterPath(result.getPosterPath());
//                        moreListResult.setReleaseDate(result.getReleaseDate());
//                        moreListResult.setTitle(result.getTitle());
//                        moreListResult.setTmdbRating(result.getVoteAverage());
//                        moreListResult.setRevenue(0.0);
//                        moreListResult.setAddedToWatchlist(status.isAddedToWatchList());
//                        moreListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
//                        moreListResult.setRegionReleaseDate(releaseDatesResult.getReleaseDates()
//                                .get(0).getReleaseDate());
//                    }
//                    moreListResults.add(moreListResult);
//                    realm.close();
//                    return moreListResults;
//
//                })
//                .delay(2, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnEach(__ -> mView.hideProgress())
//                .doOnNext(moreListResultList -> {
//                    mView.showMoviesList(moreListResultList);
//                })
//                .doOnError(throwable -> {
//                    mView.hideProgress();
//                    mView.showError(throwable.getMessage());
//                })
//                .subscribe()
//        );
//    }

    private MovieStatus findInRealmMovieStatus(Realm realm, Integer id) {
        return realm.where(MovieStatus.class).equalTo(MovieStatus.FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private UserRating findInRealmUserRating(Realm realm, Integer id) {
        return realm.where(UserRating.class).equalTo(UserRating.FIELD_MOVIE_ID, id)
                .findFirst();
    }

    //
    private void displayResults(List<MoreListResult> moreListResults) {
        mView.hideProgress();
        mView.showMoviesList(moreListResults);
    }

    private void displayError(Throwable t) {
        mView.hideProgress();
        mView.showError(t.getMessage());
    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }
}
