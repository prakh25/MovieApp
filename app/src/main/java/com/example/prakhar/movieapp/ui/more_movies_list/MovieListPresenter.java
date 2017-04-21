package com.example.prakhar.movieapp.ui.more_movies_list;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.model.home.movie.MovieResponse;
import com.example.prakhar.movieapp.model.home.movie.Result;
import com.example.prakhar.movieapp.model.more_movie_list.MovieListResult;
import com.example.prakhar.movieapp.model.movie_detail.MovieDetail;
import com.example.prakhar.movieapp.model.realm.MovieStatus;
import com.example.prakhar.movieapp.model.realm.UserRating;
import com.example.prakhar.movieapp.model.more_movie_list.box_office.BoxOffice;
import com.example.prakhar.movieapp.model.more_movie_list.box_office.RevenueComparator;
import com.example.prakhar.movieapp.network.DataManager;
import com.example.prakhar.movieapp.ui.base.BasePresenter;
import com.example.prakhar.movieapp.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prakhar.movieapp.utils.Constants.FIELD_MOVIE_ID;

/**
 * Created by Prakhar on 3/14/2017.
 */

class MovieListPresenter extends BasePresenter<MovieListContract.MoreView>
        implements MovieListContract.ViewActions {

    private final DataManager dataManager;
    private String region;
    private Realm realm;
    private Integer genreId;
    private List<MovieListResult> movieListResults;

    private static final int ITEM_REQUEST_INITIAL_PAGE = 1;

    MovieListPresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(MovieApp.getApp());
        region = countryCode.getString(Constants.COUNTRY_CODE, null);
        movieListResults = new ArrayList<>();
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
                        List<MovieListResult> movieListResults = new ArrayList<>();
                        movieListResults.addAll(getMovieList(response.body()));
                        displayResults(movieListResults);
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
                        List<MovieListResult> movieListResults = new ArrayList<>();
                        movieListResults.addAll(getMovieList(response.body()));
                        displayResults(movieListResults);
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
                        List<MovieListResult> movieListResults = new ArrayList<>();
                        movieListResults.addAll(getMovieList(response.body()));
                        displayResults(movieListResults);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        displayError(t);
                    }
                });
    }

    private List<MovieListResult> getMovieList(MovieResponse response) {
        List<Result> results = response.getResults();
        List<MovieListResult> movieListResults = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        for (Result result : results) {
            MovieListResult movieListResult = new MovieListResult();
            MovieStatus status = findInRealmMovieStatus(realm, result.getId());
            if (status == null) {
                movieListResult.setResult(result);
                movieListResult.setAddedToWatchlist(false);
                movieListResult.setMarkedAsFavorite(false);
                movieListResult.setUserRating(0);
            } else {
                if (!status.isRated()) {
                    movieListResult.setResult(result);
                    movieListResult.setAddedToWatchlist(status.isAddedToWatchList());
                    movieListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
                    movieListResult.setUserRating(0);
                } else {
                    int rating = findInRealmUserRating(realm, result.getId()).getUserRating();
                    movieListResult.setResult(result);
                    movieListResult.setAddedToWatchlist(status.isAddedToWatchList());
                    movieListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
                    movieListResult.setUserRating(rating);
                }
            }
            movieListResults.add(movieListResult);
        }
        realm.close();
        return movieListResults;
    }

    private void getBoxOfficeList() {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        dataManager.getWeekendBoxOffice(new Callback<List<BoxOffice>>() {
            @Override
            public void onResponse(Call<List<BoxOffice>> call, Response<List<BoxOffice>> response) {
                for (BoxOffice boxOffice : response.body()) {
                    getBoxOfficeMovieList(boxOffice);
                }
                Handler handler = new Handler();
                handler.postDelayed(() -> displayBoxOfficeResults(movieListResults), 1000);
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
                        setMovieListResultList(getBoxOfficeMovieDetail(pair));
                    }

                    @Override
                    public void onFailure(Call<MovieDetail> call, Throwable t) {
                    }
                });
    }

    private void setMovieListResultList(MovieListResult movieListResult) {
        movieListResults.add(movieListResult);
    }

    private MovieListResult getBoxOfficeMovieDetail(Pair pair) {

        MovieDetail movieDetail = (MovieDetail) pair.first;
        BoxOffice boxOffice = (BoxOffice) pair.second;

        realm = Realm.getDefaultInstance();
        MovieListResult movieListResult = new MovieListResult();
        Result result = createResult(movieDetail);

        MovieStatus status = findInRealmMovieStatus(realm, movieDetail.getId());
        if (status == null) {
            movieListResult.setResult(result);
            movieListResult.setRevenue(boxOffice.getRevenue());
            movieListResult.setAddedToWatchlist(false);
            movieListResult.setMarkedAsFavorite(false);
        } else {
            movieListResult.setResult(result);
            movieListResult.setRevenue(boxOffice.getRevenue());
            movieListResult.setAddedToWatchlist(status.isAddedToWatchList());
            movieListResult.setMarkedAsFavorite(status.isMarkedAsFavorite());
        }
        realm.close();

        return movieListResult;
    }

    private Result createResult(MovieDetail movieDetail) {
        Result result = new Result();
        result.setTitle(movieDetail.getTitle());
        result.setId(movieDetail.getId());
        result.setPosterPath(movieDetail.getPosterPath());
        result.setPosterPath(movieDetail.getPosterPath());
        result.setBackdropPath(movieDetail.getBackdropPath());
        result.setReleaseDate(movieDetail.getReleaseDate());
        result.setVoteAverage(movieDetail.getVoteAverage());

        return result;
    }

    private MovieStatus findInRealmMovieStatus(Realm realm, Integer id) {
        return realm.where(MovieStatus.class).equalTo(FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private UserRating findInRealmUserRating(Realm realm, Integer id) {
        return realm.where(UserRating.class).equalTo(FIELD_MOVIE_ID, id)
                .findFirst();
    }

    //
    private void displayResults(List<MovieListResult> movieListResults) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        if (movieListResults.isEmpty()) {
            mView.showEmpty();
            return;
        }
        mView.showMoviesList(movieListResults);
    }

    private void displayBoxOfficeResults(List<MovieListResult> movieListResults) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        if (movieListResults.isEmpty()) {
            mView.showEmpty();
            return;
        }
        Collections.sort(movieListResults, new RevenueComparator());
        mView.showMoviesList(movieListResults);
    }

    private void displayError(Throwable t) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(t.getMessage());
    }
}
