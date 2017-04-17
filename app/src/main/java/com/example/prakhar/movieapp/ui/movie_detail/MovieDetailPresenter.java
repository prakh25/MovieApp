package com.example.prakhar.movieapp.ui.movie_detail;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Cast;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.TmdbMovieDetail;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Video;
import com.example.prakhar.movieapp.model.movie_detail.trakt.TraktMovieRating;
import com.example.prakhar.movieapp.model.realm.Favorite;
import com.example.prakhar.movieapp.model.realm.MovieStatus;
import com.example.prakhar.movieapp.model.realm.UserList;
import com.example.prakhar.movieapp.model.realm.UserListItem;
import com.example.prakhar.movieapp.model.realm.UserRating;
import com.example.prakhar.movieapp.model.realm.WatchList;
import com.example.prakhar.movieapp.model.release_dates.ReleaseDatesResult;
import com.example.prakhar.movieapp.model.tmdb.Poster;
import com.example.prakhar.movieapp.model.tmdb.Result;
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
 * Created by Prakhar on 3/4/2017.
 */

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.DetailView>
        implements MovieDetailContract.ViewActions {

    private final DataManager dataManager;
    private Realm realm;
    private String region;
    private List<Cast> castList;
    private List<Crew> crewList;
    private List<Poster> movieImages;

    public MovieDetailPresenter(@NonNull DataManager dataManager) {
        this.dataManager = dataManager;
        SharedPreferences countryCode = PreferenceManager.getDefaultSharedPreferences(MovieApp.getApp());
        region = countryCode.getString(Constants.COUNTRY_CODE, null);
        realm = Realm.getDefaultInstance();
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
        movieImages = new ArrayList<>();
    }

    @Override
    public void onMovieRequested(Integer movieId) {
        onMovieDetailRequested(movieId);
    }

    @Override
    public void onAddToWatchlistClicked(Integer movieId, String posterPath, String overview,
                                        String backDropPath, String movieName, String releaseDate,
                                        Integer voteCount, Double voteAverage) {
        addToWatchlist(movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);
    }

    @Override
    public void onRemoveFromWatchlistClicked(Integer movieId) {
        removeFromWatchlist(movieId);
    }

    @Override
    public void onAddMarkAsFavoriteClicked(Integer movieId, String posterPath, String overview,
                                           String backDropPath, String movieName, String releaseDate,
                                           Integer voteCount, Double voteAverage) {

        markedAsFavorite(movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);
    }

    @Override
    public void onRemoveFromFavoriteList(Integer movieId) {
        removeFromFavorite(movieId);
    }

    @Override
    public void onSaveMovieRatingClicked(Integer movieId, String posterPath, String overview,
                                         String backDropPath, String movieName, String releaseDate,
                                         Integer voteCount, Double voteAverage, int userRating) {
        addUserRating(movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage, userRating);
    }

    @Override
    public void onDeleteMovieRatingClicked(Integer movieId) {
        deleteUserRating(movieId);
    }

    @Override
    public void onAddToListClicked(Integer movieId, String posterPath, String overview,
                                   String backDropPath, String movieName, String releaseDate,
                                   Integer voteCount, Double voteAverage) {
        addToList(movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);
    }

    @Override
    public void onCreateNewListRequested(@NonNull String title, String description, Integer movieId,
                                         String posterPath, String overview,
                                         String backDropPath, String movieName, String releaseDate,
                                         Integer voteCount, Double voteAverage) {
        createNewUserList(title, description, movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);
    }

    @Override
    public void onAddMovieToList(int listId, Integer movieId, String posterPath, String overview,
                                 String backDropPath, String movieName, String releaseDate,
                                 Integer voteCount, Double voteAverage) {
        addMovieToUserList(listId, movieId, posterPath, overview, backDropPath, movieName,
                releaseDate, voteCount, voteAverage);
    }

    private void onMovieDetailRequested(Integer movieId) {

        mView.showProgress();
        dataManager.getMovieDetail(movieId,
                new Callback<TmdbMovieDetail>() {
                    @Override
                    public void onResponse(Call<TmdbMovieDetail> call, Response<TmdbMovieDetail> response) {
                        if(response.code() == 200) {
                            String imdbId = response.body().getImdbId();
                            if (imdbId != null && !imdbId.isEmpty() && !imdbId.equals("")) {
                                getTraktMovieRating(response.body());
                            } else {
                                TraktMovieRating traktMovieRating = new TraktMovieRating();
                                Pair<TraktMovieRating, TmdbMovieDetail> pair =
                                        new Pair<>(traktMovieRating, response.body());
                                showMovieDetail(pair);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TmdbMovieDetail> call, Throwable t) {
                        showError(t);
                    }
                });
    }

    private void getTraktMovieRating(TmdbMovieDetail movieDetail) {

        dataManager.getTraktMovieRating(movieDetail.getImdbId(),
                new Callback<TraktMovieRating>() {
                    @Override
                    public void onResponse(Call<TraktMovieRating> call, Response<TraktMovieRating> response) {
                        if(response.code() == 200) {
                            Pair<TraktMovieRating, TmdbMovieDetail> pair =
                                    new Pair<>(response.body(), movieDetail);
                            showMovieDetail(pair);
                        } else {
                            TraktMovieRating traktMovieRating = new TraktMovieRating();
                            Pair<TraktMovieRating, TmdbMovieDetail> pair =
                                    new Pair<>(traktMovieRating, movieDetail);
                            showMovieDetail(pair);
                        }
                    }

                    @Override
                    public void onFailure(Call<TraktMovieRating> call, Throwable t) {
                        showError(t);
                    }
                });
    }

    private void showMovieDetail(Pair moviePair) {

        if(!isViewAttached()) return;
        mView.hideProgress();

        TraktMovieRating traktMovieRating = (TraktMovieRating) moviePair.first;

        TmdbMovieDetail tmdbMovieDetail = (TmdbMovieDetail) moviePair.second;

        MovieStatus realmResult = findInRealmMovieStatus(realm, tmdbMovieDetail.getId());
        if (realmResult == null) {
            mView.showMovieStatus(tmdbMovieDetail.getId(), tmdbMovieDetail.getPosterPath(),
                    tmdbMovieDetail.getOverview(), tmdbMovieDetail.getBackdropPath(),
                    tmdbMovieDetail.getTitle(), tmdbMovieDetail.getReleaseDate(),
                    tmdbMovieDetail.getVoteCount(), tmdbMovieDetail.getVoteAverage(),
                    false, false);
        } else {
            mView.showMovieStatus(tmdbMovieDetail.getId(), tmdbMovieDetail.getPosterPath(),
                    tmdbMovieDetail.getOverview(), tmdbMovieDetail.getBackdropPath(),
                    tmdbMovieDetail.getTitle(), tmdbMovieDetail.getReleaseDate(),
                    tmdbMovieDetail.getVoteCount(), tmdbMovieDetail.getVoteAverage(),
                    realmResult.isAddedToWatchList(), realmResult.isMarkedAsFavorite());
        }

        UserRating userRating = findInRealmRatings(realm, tmdbMovieDetail.getId());
        Integer rating;

        if (userRating == null) {
            rating = 0;
        } else {
            rating = userRating.getUserRating();
        }

        if(traktMovieRating.getRating() != null) {
            mView.showRatings(tmdbMovieDetail.getId(), tmdbMovieDetail.getPosterPath(),
                    tmdbMovieDetail.getOverview(), tmdbMovieDetail.getBackdropPath(),
                    tmdbMovieDetail.getTitle(), tmdbMovieDetail.getReleaseDate(),
                    tmdbMovieDetail.getVoteCount(), tmdbMovieDetail.getVoteAverage(),
                    rating, tmdbMovieDetail.getVoteAverage(), tmdbMovieDetail.getVoteCount(),
                    traktMovieRating.getRating(), traktMovieRating.getVotes());
        } else {
            mView.showRatings(tmdbMovieDetail.getId(), tmdbMovieDetail.getPosterPath(),
                    tmdbMovieDetail.getOverview(), tmdbMovieDetail.getBackdropPath(),
                    tmdbMovieDetail.getTitle(), tmdbMovieDetail.getReleaseDate(),
                    tmdbMovieDetail.getVoteCount(), tmdbMovieDetail.getVoteAverage(),
                    rating, tmdbMovieDetail.getVoteAverage(), tmdbMovieDetail.getVoteCount(),
                    0.0, 0);
        }

        if (!tmdbMovieDetail.getVideoResponse().getResults().isEmpty()) {
            String key = null;
            for (Video video : tmdbMovieDetail.getVideoResponse().getResults()) {
                if (video.getType().equalsIgnoreCase("trailer")) {
                    key = video.getKey();
                    break;
                }
            }
            if (key == null) {
                for (Video video : tmdbMovieDetail.getVideoResponse().getResults()) {
                    if (video.getType().contains("teaser")) {
                        key = video.getKey();
                        break;
                    }
                }
            }
            Timber.i("videoId" + key);
            mView.showMovieTrailer(key);
        }

        String releaseDate = null;
        String country = "US";
        for (ReleaseDatesResult result : tmdbMovieDetail.getReleaseDateResponse().getResults()) {
            if (result.getIso31661().equalsIgnoreCase(region)) {
                releaseDate = result.getReleaseDates().get(0).getReleaseDate();
                country = result.getIso31661();
                break;
            }
        }

        if (releaseDate == null) {
            for (ReleaseDatesResult result : tmdbMovieDetail.getReleaseDateResponse().getResults()) {
                if (result.getIso31661().equalsIgnoreCase(country)) {
                    releaseDate = result.getReleaseDates().get(0).getReleaseDate();
                    break;
                }
            }
        }

        mView.showMovieDetails(releaseDate, country,
                tmdbMovieDetail.getTagline(), tmdbMovieDetail.getOverview());

        if (tmdbMovieDetail.getCredits().getCast().size() >= 3) {
            mView.showMovieCast(tmdbMovieDetail.getCredits().getCast().subList(0, 3));
            setCastList(tmdbMovieDetail.getCredits().getCast());
        } else if(!tmdbMovieDetail.getCredits().getCast().isEmpty()) {
            mView.showMovieCast(tmdbMovieDetail.getCredits().getCast());
            setCastList(tmdbMovieDetail.getCredits().getCast());
        }

        if (!tmdbMovieDetail.getCredits().getCrew().isEmpty()) {
            List<Crew> director = new ArrayList<>();
            List<Crew> writers = new ArrayList<>();
            List<Crew> screenPlay = new ArrayList<>();

            for (Crew crew : tmdbMovieDetail.getCredits().getCrew()) {
                if (crew.getJob().equalsIgnoreCase("director")) {
                    director.add(crew);
                }
                if (crew.getJob().equalsIgnoreCase("story") ||
                        crew.getJob().equalsIgnoreCase("writer")) {
                    writers.add(crew);
                }
                if (crew.getJob().equalsIgnoreCase("screenplay")) {
                    screenPlay.add(crew);
                }
            }

            mView.showMovieCrew(director, writers, screenPlay);

            setCrewList(tmdbMovieDetail.getCredits().getCrew());
        }

        if (!tmdbMovieDetail.getImages().getBackdrops().isEmpty() ||
                !tmdbMovieDetail.getImages().getPosters().isEmpty()) {
            List<Poster> images = new ArrayList<>(tmdbMovieDetail.getImages().getBackdrops());
            images.addAll(tmdbMovieDetail.getImages().getPosters());
            if (images.size() > 5) {
                mView.showMovieImages(images.subList(0, 5), images.size(), true);
            } else {
                mView.showMovieImages(images, images.size(), false);
            }

            setMovieImages(images);
        }

        if (tmdbMovieDetail.getBelongsToCollection() != null) {
            mView.showBelongToCollection(tmdbMovieDetail.getBelongsToCollection());
        }

        if (!tmdbMovieDetail.getSimilar().getResults().isEmpty()) {
            mView.showSimilarMovies(tmdbMovieDetail.getSimilar().getResults());
        }

        mView.showExternalLinks(tmdbMovieDetail.getHomepage(), tmdbMovieDetail.getId(),
                tmdbMovieDetail.getImdbId(), tmdbMovieDetail.getTitle());
    }

    private void showError(Throwable throwable) {
        if(!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }

    private void addToWatchlist(Integer movieId, String posterPath, String overview,
                                String backDropPath, String movieName,
                                String releaseDate, Integer voteCount,
                                Double voteAverage) {

        realm.executeTransactionAsync(realm1 -> {
            WatchList watchListRealm = findInRealmWatchList(realm1, movieId);
            if (watchListRealm == null) {
                watchListRealm = realm1.createObject(WatchList.class, movieId);
                watchListRealm.setTitle(movieName);
                watchListRealm.setPosterPath(posterPath);
                watchListRealm.setOverview(overview);
                watchListRealm.setVoteCount(voteCount);
                watchListRealm.setVoteAverage(voteAverage);
                watchListRealm.setReleaseDate(releaseDate);
                watchListRealm.setBackdropPath(backDropPath);

                MovieStatus status = findInRealmMovieStatus(realm1, movieId);
                if (status != null) {
                    status.setAddedToWatchList(true);
                } else {
                    realm1.createObject(MovieStatus.class, movieId)
                            .setAddedToWatchList(true);
                }
            } else {
                Timber.i(movieId + "Already present in watchlist");
            }
        });
    }

    private void removeFromWatchlist(Integer movieId) {
        realm.executeTransactionAsync(realm1 -> {
            WatchList watchListRealm = findInRealmWatchList(realm1, movieId);
            if (watchListRealm != null) {
                watchListRealm.deleteFromRealm();
                MovieStatus status = findInRealmMovieStatus(realm1, movieId);
                status.setAddedToWatchList(false);
            } else {
                Timber.i("No such id present");
            }
        });
    }

    private void markedAsFavorite(Integer movieId, String posterPath, String overview,
                                  String backDropPath, String movieName,
                                  String releaseDate, Integer voteCount,
                                  Double voteAverage) {

        realm.executeTransactionAsync(realm1 -> {
            Favorite favoriteRealm = findInRealmFavorite(realm1, movieId);
            if (favoriteRealm == null) {
                favoriteRealm = realm1.createObject(Favorite.class, movieId);
                favoriteRealm.setTitle(movieName);
                favoriteRealm.setPosterPath(posterPath);
                favoriteRealm.setOverview(overview);
                favoriteRealm.setVoteCount(voteCount);
                favoriteRealm.setVoteAverage(voteAverage);
                favoriteRealm.setReleaseDate(releaseDate);
                favoriteRealm.setBackdropPath(backDropPath);

                MovieStatus status = findInRealmMovieStatus(realm1,movieId);
                if (status != null) {
                    status.setMarkedAsFavorite(true);
                } else {
                    realm1.createObject(MovieStatus.class, movieId)
                            .setMarkedAsFavorite(true);
                }
            } else {
                Timber.i(movieId + "Already present in watchlist");
            }
        });
    }

    private void removeFromFavorite(Integer movieId) {
        realm.executeTransactionAsync(realm1 -> {
            Favorite favoriteRealm = findInRealmFavorite(realm1, movieId);
            if (favoriteRealm != null) {
                favoriteRealm.deleteFromRealm();

                MovieStatus status = findInRealmMovieStatus(realm1, movieId);
                status.setMarkedAsFavorite(false);
            } else {
                Timber.i("No such id present");
            }
        });
    }

    private void addUserRating(Integer movieId, String posterPath, String overview,
                               String backDropPath, String movieName, String releaseDate,
                               Integer voteCount, Double voteAverage, int userRating) {

        realm.executeTransactionAsync(realm1 -> {
            UserRating ratingRealm = findInRealmRatings(realm1, movieId);
            if (ratingRealm == null) {
                ratingRealm = realm1.createObject(UserRating.class, movieId);
                ratingRealm.setTitle(movieName);
                ratingRealm.setPosterPath(posterPath);
                ratingRealm.setOverview(overview);
                ratingRealm.setVoteCount(voteCount);
                ratingRealm.setVoteAverage(voteAverage);
                ratingRealm.setReleaseDate(releaseDate);
                ratingRealm.setBackdropPath(backDropPath);
                ratingRealm.setUserRating(userRating);
                MovieStatus status = findInRealmMovieStatus(realm1, movieId);
                if (status != null) {
                    status.setRated(true);
                } else {
                    realm1.createObject(MovieStatus.class, movieId)
                            .setRated(true);
                }
            } else {
                ratingRealm.setUserRating(userRating);
            }
        });
    }

    private void deleteUserRating(Integer movieId) {
        realm.executeTransactionAsync(realm1 -> {
            UserRating realmRatings = findInRealmRatings(realm1, movieId);
            if (realmRatings != null) {
                realmRatings.deleteFromRealm();
                MovieStatus status = findInRealmMovieStatus(realm1, movieId);
                status.setRated(false);
            } else {
                Timber.i("No such id present");
            }
        });
    }

    private void addToList(Integer movieId, String posterPath, String overview,
                           String backDropPath, String movieName, String releaseDate,
                           Integer voteCount, Double voteAverage) {

        Result result = new Result();

        result.setId(movieId);
        result.setPosterPath(posterPath);
        result.setOverview(overview);
        result.setBackdropPath(backDropPath);
        result.setTitle(movieName);
        result.setReleaseDate(releaseDate);
        result.setVoteCount(voteCount);
        result.setVoteAverage(voteAverage);

        List<UserList> userListRealm = findInRealmUserLists(realm);
        if (userListRealm == null) {
            mView.showAddToListDialog(null, result);
        } else {
            List<String> listName = new ArrayList<>();
            for (UserList userlist : userListRealm) {
                listName.add(userlist.getName());
            }
            mView.showAddToListDialog(listName, result);
        }
    }

    private void createNewUserList(String listTitle, String listDescription, Integer movieId,
                                   String posterPath, String overview,
                                   String backDropPath, String movieName, String releaseDate,
                                   Integer voteCount, Double voteAverage) {

        realm.executeTransactionAsync(realm1 -> {

            UserList userList = realm1.createObject(UserList.class, nextListKey(realm1));
            userList.setName(listTitle);
            userList.setDescription(listDescription);

            UserListItem userListItem = findInRealmUserListItem(realm1, movieId);
            if (userListItem == null) {
                userListItem = realm1.createObject(UserListItem.class, movieId);
                userListItem.setTitle(movieName);
                userListItem.setPosterPath(posterPath);
                userListItem.setOverview(overview);
                userListItem.setVoteCount(voteCount);
                userListItem.setVoteAverage(voteAverage);
                userListItem.setReleaseDate(releaseDate);
                userListItem.setBackdropPath(backDropPath);

                userList.getItemList().add(userListItem);
            } else {
                userList.getItemList().add(userListItem);
            }

        });
    }

    private void addMovieToUserList(int listId, Integer movieId, String posterPath, String overview,
                                    String backDropPath, String movieName, String releaseDate,
                                    Integer voteCount, Double voteAverage) {

        realm.executeTransactionAsync(realm1 -> {
            UserList userList = findInRealmUserList(realm1, listId);

            UserListItem userListItem = findInRealmUserListItem(realm1, movieId);
            if (userListItem == null) {
                userListItem = realm1.createObject(UserListItem.class, movieId);
                userListItem.setTitle(movieName);
                userListItem.setPosterPath(posterPath);
                userListItem.setOverview(overview);
                userListItem.setVoteCount(voteCount);
                userListItem.setVoteAverage(voteAverage);
                userListItem.setReleaseDate(releaseDate);
                userListItem.setBackdropPath(backDropPath);

                userList.getItemList().add(userListItem);
            } else {
                boolean isAlreadyPresent = false;
                for (UserListItem item : userList.getItemList()) {
                    if (item.getMovieId().equals(movieId)) {
                        isAlreadyPresent = true;
                        break;
                    }
                }
                if (isAlreadyPresent) {
                    return;
                } else {
                    userList.getItemList().add(userListItem);
                }
            }
        });
    }

    private WatchList findInRealmWatchList(Realm realm, Integer id) {
        return realm.where(WatchList.class).equalTo(WatchList.FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private Favorite findInRealmFavorite(Realm realm, Integer id) {
        return realm.where(Favorite.class).equalTo(Favorite.FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private UserRating findInRealmRatings(Realm realm, Integer id) {
        return realm.where(UserRating.class).equalTo(UserRating.FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private MovieStatus findInRealmMovieStatus(Realm realm, Integer id) {
        return realm.where(MovieStatus.class).equalTo(MovieStatus.FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private List<UserList> findInRealmUserLists(Realm realm) {
        return realm.where(UserList.class).findAllAsync();
    }

    private UserList findInRealmUserList(Realm realm, int id) {
        return realm.where(UserList.class).equalTo(UserList.FIELD_LIST_ID, id).findFirst();
    }

    private UserListItem findInRealmUserListItem(Realm realm, Integer id) {
        return realm.where(UserListItem.class).equalTo(UserListItem.FIELD_USER_LIST_ITEM_ID, id)
                .findFirst();
    }

    private int nextListKey(Realm realm) {
        try {
            Number currentId = realm.where(UserList.class).max(UserList.FIELD_LIST_ID);
            if (currentId == null) {
                return 1;
            } else {
                return (currentId.intValue() + 1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    private void setCastList(List<Cast> castList) {
        this.castList = castList;
    }

    public List<Cast> getCastList() {
        return castList;
    }

    private void setCrewList(List<Crew> crewList) {
        this.crewList = crewList;
    }

    public List<Crew> getCrewList() {
        return crewList;
    }

    private void setMovieImages(List<Poster> images) {
        movieImages = images;
    }

    public List<Poster> getMovieImages() {
        return movieImages;
    }

    public void onDestroy() {
        realm.close();
    }
}
