package com.example.prakhar.movieapp.ui.more_movies_list;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.model.more.MoreListResult;
import com.example.prakhar.movieapp.model.movie_detail.MovieDetail;
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

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Prakhar on 3/14/2017.
 */

public class MorePresenter extends BasePresenter<MoreContract.MoreView>
        implements MoreContract.ViewActions {

    private final DataManager dataManager;
    private String region;
    private Realm realm;
    private Integer genreId;
    private MoreListResult moreListResult;

    private static final int ITEM_REQUEST_INITIAL_PAGE = 1;

    public MorePresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(MovieApp.getApp());
        region = countryCode.getString(Constants.COUNTRY_CODE, null);
    }

    @Override
    public void mostPopularListRequested() {
        getPopularMovies(ITEM_REQUEST_INITIAL_PAGE);
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
                getPopularMovies(pageNo);
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

    private void getPopularMovies(int page) {

        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getPopularMovies(region, page,
                new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<MoreListResult> moreListResults = new ArrayList<>();
                        moreListResults.addAll(getMoreList(response.body()));
                        displayResults(moreListResults);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
    }

    private void getTopRatedMovies(int page) {

        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getTopRatedMovies(region, page,
                new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<MoreListResult> moreListResults = new ArrayList<>();
                        moreListResults.addAll(getMoreList(response.body()));
                        displayResults(moreListResults);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
    }

    private void getMoviesByGenre(Integer page, Integer genreId) {

        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getMoviesByGenre(region, String.valueOf(genreId), page,
                new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<MoreListResult> moreListResults = new ArrayList<>();
                        moreListResults.addAll(getMoreList(response.body()));
                        displayResults(moreListResults);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
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
        dataManager.getWeekendBoxOffice(new Callback<List<BoxOffice>>() {
            @Override
            public void onResponse(Call<List<BoxOffice>> call, Response<List<BoxOffice>> response) {
                List<MoreListResult> moreListResults = new ArrayList<>();
                for (BoxOffice boxOffice : response.body()) {
                    getBoxOfficeMovieList(boxOffice);
                    moreListResults.add(getMoreListResult());
                }
                displayResults(moreListResults);
            }

            @Override
            public void onFailure(Call<List<BoxOffice>> call, Throwable t) {
                displayError(t);
            }
        });
    }

    private void getBoxOfficeMovieList(BoxOffice boxOffice) {
        Integer tmdbId = boxOffice.getMovie().getIds().getTmdb();

        dataManager.getBoxOfficeMovieDetail(tmdbId,
                new Callback<MovieDetail>() {
                    @Override
                    public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                        Pair<MovieDetail, BoxOffice> pair = new Pair<>(response.body(), boxOffice);
                        MoreListResult moreListResults = getBoxOfficeMovieDetail(pair);
                        setMoreListResult(moreListResults);
                    }

                    @Override
                    public void onFailure(Call<MovieDetail> call, Throwable t) {

                    }
                });
    }

    private MoreListResult getBoxOfficeMovieDetail(Pair pair) {
        MovieDetail movieDetail = (MovieDetail) pair.first;
        BoxOffice boxOffice = (BoxOffice) pair.second;

        realm = Realm.getDefaultInstance();

        MoreListResult moreListResult = new MoreListResult();

        MovieStatus status = findInRealmMovieStatus(realm, movieDetail.getId());
        if (status == null) {
            Result result = new Result();
            result.setTitle(movieDetail.getTitle());
            result.setId(movieDetail.getId());
            result.setPosterPath(movieDetail.getPosterPath());
            moreListResult.setResult(result);
            moreListResult.setRevenue(boxOffice.getRevenue());
            moreListResult.setAddedToWatchlist(false);
            moreListResult.setMarkedAsFavorite(false);
        } else {
            Result result = new Result();
            result.setTitle(movieDetail.getTitle());
            result.setId(movieDetail.getId());
            result.setPosterPath(movieDetail.getPosterPath());
            moreListResult.setResult(result);
            moreListResult.setRevenue(boxOffice.getRevenue());
            moreListResult.setAddedToWatchlist(status.isAddedToWatchList());
            moreListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
        }
        realm.close();

        return moreListResult;
    }

    private void setMoreListResult(MoreListResult moreListResult) {
        this.moreListResult = moreListResult;
    }

    private MoreListResult getMoreListResult() {
        return moreListResult;
    }

    private MovieStatus findInRealmMovieStatus(Realm realm, Integer id) {
        return realm.where(MovieStatus.class).equalTo(MovieStatus.FIELD_MOVIE_ID, id)
                .findFirstAsync();
    }

    private UserRating findInRealmUserRating(Realm realm, Integer id) {
        return realm.where(UserRating.class).equalTo(UserRating.FIELD_MOVIE_ID, id)
                .findFirstAsync();
    }

    //
    private void displayResults(List<MoreListResult> moreListResults) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        if (moreListResults.isEmpty()) {
            mView.showEmpty();
            return;
        }
        mView.showMoviesList(moreListResults);
    }

    private void displayError(Throwable t) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(t.getMessage());
    }
}
