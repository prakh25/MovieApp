package com.example.prakhar.movieapp.ui.movie_detail;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.example.prakhar.movieapp.MovieApp;
import com.example.prakhar.movieapp.model.movie_detail.GenericMovieDataWrapper;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Cast;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.TmdbMovieDetail;
import com.example.prakhar.movieapp.model.movie_detail.tmdb.Video;
import com.example.prakhar.movieapp.model.movie_detail.trakt.TraktMovieRating;
import com.example.prakhar.movieapp.model.realm.Favorite;
import com.example.prakhar.movieapp.model.realm.MovieItem;
import com.example.prakhar.movieapp.model.realm.MovieStatus;
import com.example.prakhar.movieapp.model.realm.UserList;
import com.example.prakhar.movieapp.model.realm.UserRating;
import com.example.prakhar.movieapp.model.realm.WatchList;
import com.example.prakhar.movieapp.model.release_dates.ReleaseDatesResult;
import com.example.prakhar.movieapp.model.home.movie.Poster;
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

import static com.example.prakhar.movieapp.model.realm.UserList.FIELD_LIST_ID;
import static com.example.prakhar.movieapp.utils.Constants.FIELD_LIST_ID;
import static com.example.prakhar.movieapp.utils.Constants.FIELD_MOVIE_ID;

/**
 * Created by Prakhar on 3/4/2017.
 */

class MovieDetailPresenter extends BasePresenter<MovieDetailContract.DetailView>
        implements MovieDetailContract.ViewActions {

    private final DataManager dataManager;
    private Realm realm;
    private String region;
    private List<Cast> castList;
    private List<Crew> crewList;
    private List<Poster> movieImages;
    private TraktMovieRating traktMovieRating;
    private TmdbMovieDetail tmdbMovieDetail;

    MovieDetailPresenter(@NonNull DataManager dataManager) {
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
    public void onAddToWatchlistClicked(GenericMovieDataWrapper wrapper) {
        addToWatchlist(wrapper);
    }

    @Override
    public void onRemoveFromWatchlistClicked(Integer movieId) {
        removeFromWatchlist(movieId);
    }

    @Override
    public void onAddMarkAsFavoriteClicked(GenericMovieDataWrapper wrapper) {

        markedAsFavorite(wrapper);
    }

    @Override
    public void onRemoveFromFavoriteList(Integer movieId) {
        removeFromFavorite(movieId);
    }

    @Override
    public void onSaveMovieRatingClicked(GenericMovieDataWrapper wrapper, int userRating) {
        addUserRating(wrapper, userRating);
    }

    @Override
    public void onDeleteMovieRatingClicked(Integer movieId) {
        deleteUserRating(movieId);
    }

    @Override
    public void onAddToListClicked(GenericMovieDataWrapper wrapper) {
        addToList(wrapper);
    }

    @Override
    public void onCreateNewListRequested(@NonNull String title, String description,
                                         GenericMovieDataWrapper wrapper) {
        createNewUserList(title, description, wrapper);
    }

    @Override
    public void onAddMovieToList(int listId, GenericMovieDataWrapper wrapper) {
        addMovieToUserList(listId, wrapper);
    }

    private void onMovieDetailRequested(Integer movieId) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();

        dataManager.getMovieDetail(movieId,
                new Callback<TmdbMovieDetail>() {
                    @Override
                    public void onResponse(Call<TmdbMovieDetail> call, Response<TmdbMovieDetail> response) {
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
                        if (response.code() == 200) {
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

        traktMovieRating = (TraktMovieRating) moviePair.first;
        tmdbMovieDetail = (TmdbMovieDetail) moviePair.second;

        mView.showMovieHeader(tmdbMovieDetail.getPosterPath(), tmdbMovieDetail.getBackdropPath(),
                tmdbMovieDetail.getTitle(), tmdbMovieDetail.getReleaseDate(),
                tmdbMovieDetail.getGenres(),
                tmdbMovieDetail.getRuntime());

        Handler handler = new Handler();

        handler.postDelayed(() -> {

            if (!isViewAttached()) return;
            mView.hideProgress();

            showMovieStatus();

            showMovieRating();

            if (!tmdbMovieDetail.getVideoResponse().getResults().isEmpty()) {
                showMovieTrailer();
            }
            showMovieOverview();
            if(!tmdbMovieDetail.getCredits().getCast().isEmpty()) {
                showMovieCast();
            }
            if (!tmdbMovieDetail.getCredits().getCrew().isEmpty()) {
                showMovieCrew();
            }
            if (!tmdbMovieDetail.getImages().getBackdrops().isEmpty() ||
                    !tmdbMovieDetail.getImages().getPosters().isEmpty()) {
                showMovieImages();
            }
            if (tmdbMovieDetail.getBelongsToCollection() != null) {
                mView.showBelongToCollection(tmdbMovieDetail.getBelongsToCollection());
            }
            if (!tmdbMovieDetail.getSimilar().getResults().isEmpty()) {
                mView.showSimilarMovies(tmdbMovieDetail.getSimilar().getResults());
            }
            mView.showExternalLinks(tmdbMovieDetail.getHomepage(), tmdbMovieDetail.getId(),
                    tmdbMovieDetail.getImdbId(), tmdbMovieDetail.getTitle());
        }, 1000);
    }

    private void showMovieStatus() {
        MovieStatus realmResult = findInRealmMovieStatus(realm, tmdbMovieDetail.getId());

        GenericMovieDataWrapper wrapper = createGenericMovieWrapper();

        if (realmResult == null) {
            mView.showMovieStatus(wrapper, false, false);
        } else {
            mView.showMovieStatus(wrapper, realmResult.isAddedToWatchList(),
                    realmResult.isMarkedAsFavorite());
        }
    }

    private void showMovieRating() {

        GenericMovieDataWrapper ratingsWrapper = createGenericMovieWrapper();

        UserRating userRating = findInRealmRatings(realm, tmdbMovieDetail.getId());

        Integer rating;

        if (userRating == null) {
            rating = 0;
        } else {
            rating = userRating.getUserRating();
        }

        if (traktMovieRating.getRating() != null) {
            mView.showRatings(ratingsWrapper, rating,
                    traktMovieRating.getRating(), traktMovieRating.getVotes());
        } else {
            mView.showRatings(ratingsWrapper, rating, 0.0, 0);
        }
    }

    private void showMovieTrailer() {
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
        mView.showMovieTrailer(key);
    }

    private void showMovieOverview() {
        String releaseDate = null;
        String defaultRegion = "US";

        for (ReleaseDatesResult result : tmdbMovieDetail.getReleaseDateResponse().getResults()) {
            if (result.getIso31661().equalsIgnoreCase(region)) {
                releaseDate = result.getReleaseDates().get(0).getReleaseDate();
                defaultRegion = result.getIso31661();
                break;
            }
        }

        if (releaseDate == null) {
            for (ReleaseDatesResult result : tmdbMovieDetail.getReleaseDateResponse().getResults()) {
                if (result.getIso31661().equalsIgnoreCase(defaultRegion)) {
                    releaseDate = result.getReleaseDates().get(0).getReleaseDate();
                    break;
                }
            }
        }

        mView.showMovieDetails(releaseDate, defaultRegion,
                tmdbMovieDetail.getTagline(), tmdbMovieDetail.getOverview());
    }

    private void showMovieCast() {
        if (tmdbMovieDetail.getCredits().getCast().size() >= 3) {
            mView.showMovieCast(tmdbMovieDetail.getCredits().getCast().subList(0, 3));
            setCastList(tmdbMovieDetail.getCredits().getCast());
        } else {
            mView.showMovieCast(tmdbMovieDetail.getCredits().getCast());
            setCastList(tmdbMovieDetail.getCredits().getCast());
        }
    }

    private void showMovieCrew() {
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

    private void showMovieImages() {
        List<Poster> images = new ArrayList<>(tmdbMovieDetail.getImages().getBackdrops());
        images.addAll(tmdbMovieDetail.getImages().getPosters());
        if (images.size() > 5) {
            mView.showMovieImages(images.subList(0, 5), images.size(), true);
        } else {
            mView.showMovieImages(images, images.size(), false);
        }
        setMovieImages(images);
    }

    private void showError(Throwable throwable) {
        if (!isViewAttached()) return;
        mView.hideProgress();
        mView.showError(throwable.getMessage());
    }

    private void addToWatchlist(GenericMovieDataWrapper wrapper) {

        realm.executeTransactionAsync(realm1 -> {
            WatchList watchListRealm = findInRealmWatchList(realm1, wrapper.getMovieId());
            if (watchListRealm == null) {
                watchListRealm = realm1.createObject(WatchList.class, wrapper.getMovieId());
                watchListRealm.setTitle(wrapper.getTitle());
                watchListRealm.setPosterPath(wrapper.getPosterPath());
                watchListRealm.setOverview(wrapper.getOverview());
                watchListRealm.setVoteCount(wrapper.getVoteCount());
                watchListRealm.setVoteAverage(wrapper.getVoteAverage());
                watchListRealm.setReleaseDate(wrapper.getReleaseDate());
                watchListRealm.setBackdropPath(wrapper.getBackdropPath());

                MovieStatus status = findInRealmMovieStatus(realm1, wrapper.getMovieId());
                if (status != null) {
                    status.setAddedToWatchList(true);
                } else {
                    realm1.createObject(MovieStatus.class, wrapper.getMovieId())
                            .setAddedToWatchList(true);
                }
            } else {
                Timber.i(wrapper.getTitle() + "Already present in watchlist");
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

    private void markedAsFavorite(GenericMovieDataWrapper wrapper) {

        realm.executeTransactionAsync(realm1 -> {
            Favorite favoriteRealm = findInRealmFavorite(realm1, wrapper.getMovieId());
            if (favoriteRealm == null) {
                favoriteRealm = realm1.createObject(Favorite.class, wrapper.getMovieId());
                favoriteRealm.setTitle(wrapper.getTitle());
                favoriteRealm.setPosterPath(wrapper.getPosterPath());
                favoriteRealm.setOverview(wrapper.getOverview());
                favoriteRealm.setVoteCount(wrapper.getVoteCount());
                favoriteRealm.setVoteAverage(wrapper.getVoteAverage());
                favoriteRealm.setReleaseDate(wrapper.getReleaseDate());
                favoriteRealm.setBackdropPath(wrapper.getBackdropPath());

                MovieStatus status = findInRealmMovieStatus(realm1, wrapper.getMovieId());
                if (status != null) {
                    status.setMarkedAsFavorite(true);
                } else {
                    realm1.createObject(MovieStatus.class, wrapper.getMovieId())
                            .setMarkedAsFavorite(true);
                }
            } else {
                Timber.i(wrapper.getTitle() + "Already present in watchlist");
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

    private void addUserRating(GenericMovieDataWrapper wrapper, int userRating) {

        realm.executeTransactionAsync(realm1 -> {
            UserRating ratingRealm = findInRealmRatings(realm1, wrapper.getMovieId());
            if (ratingRealm == null) {
                ratingRealm = realm1.createObject(UserRating.class, wrapper.getMovieId());
                ratingRealm.setTitle(wrapper.getTitle());
                ratingRealm.setPosterPath(wrapper.getPosterPath());
                ratingRealm.setOverview(wrapper.getOverview());
                ratingRealm.setVoteCount(wrapper.getVoteCount());
                ratingRealm.setVoteAverage(wrapper.getVoteAverage());
                ratingRealm.setReleaseDate(wrapper.getReleaseDate());
                ratingRealm.setBackdropPath(wrapper.getBackdropPath());
                ratingRealm.setUserRating(userRating);
                MovieStatus status = findInRealmMovieStatus(realm1, wrapper.getMovieId());
                if (status != null) {
                    status.setRated(true);
                } else {
                    realm1.createObject(MovieStatus.class, wrapper.getMovieId())
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

    private void addToList(GenericMovieDataWrapper wrapper) {

//        Result result = new Result();
//
//        result.setId(movieId);
//        result.setPosterPath(posterPath);
//        result.setOverview(overview);
//        result.setBackdropPath(backDropPath);
//        result.setTitle(movieName);
//        result.setReleaseDate(releaseDate);
//        result.setVoteCount(voteCount);
//        result.setVoteAverage(voteAverage);

        List<UserList> userListRealm = findInRealmUserLists(realm);
        if (userListRealm == null) {
            mView.showAddToListDialog(null, wrapper);
        } else {
            List<String> listName = new ArrayList<>();
            for (UserList userlist : userListRealm) {
                listName.add(userlist.getName());
            }
            mView.showAddToListDialog(listName, wrapper);
        }
    }

    private void createNewUserList(String listTitle, String listDescription,
                                   GenericMovieDataWrapper wrapper) {

        realm.executeTransactionAsync(realm1 -> {

            UserList userList = realm1.createObject(UserList.class, nextListKey(realm1));
            userList.setName(listTitle);
            userList.setDescription(listDescription);

            UserListItem userListItem = findInRealmUserListItem(realm1, wrapper.getMovieId());
            if (userListItem == null) {
                userListItem = realm1.createObject(UserListItem.class, wrapper.getMovieId());
                userListItem.setTitle(wrapper.getTitle());
                userListItem.setPosterPath(wrapper.getPosterPath());
                userListItem.setOverview(wrapper.getOverview());
                userListItem.setVoteCount(wrapper.getVoteCount());
                userListItem.setVoteAverage(wrapper.getVoteAverage());
                userListItem.setReleaseDate(wrapper.getReleaseDate());
                userListItem.setBackdropPath(wrapper.getBackdropPath());

                userList.getItemList().add(userListItem);
            } else {
                userList.getItemList().add(userListItem);
            }

        });
    }

    private void addMovieToUserList(int listId, GenericMovieDataWrapper wrapper) {

        realm.executeTransactionAsync(realm1 -> {
            UserList userList = findInRealmUserList(realm1, listId);

            UserListItem userListItem = findInRealmUserListItem(realm1, wrapper.getMovieId());
            if (userListItem == null) {
                userListItem = realm1.createObject(UserListItem.class, wrapper.getMovieId());
                userListItem.setTitle(wrapper.getTitle());
                userListItem.setPosterPath(wrapper.getPosterPath());
                userListItem.setOverview(wrapper.getOverview());
                userListItem.setVoteCount(wrapper.getVoteCount());
                userListItem.setVoteAverage(wrapper.getVoteAverage());
                userListItem.setReleaseDate(wrapper.getReleaseDate());
                userListItem.setBackdropPath(wrapper.getBackdropPath());

                userList.getItemList().add(userListItem);
            } else {
                boolean isAlreadyPresent = false;
                for (UserListItem item : userList.getItemList()) {
                    if (item.getMovieId().equals(wrapper.getMovieId())) {
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

    private GenericMovieDataWrapper createGenericMovieWrapper() {

        GenericMovieDataWrapper wrapper = new GenericMovieDataWrapper();
        wrapper.setMovieId(tmdbMovieDetail.getId());
        wrapper.setPosterPath(tmdbMovieDetail.getPosterPath());
        wrapper.setOverview(tmdbMovieDetail.getOverview());
        wrapper.setBackdropPath(tmdbMovieDetail.getBackdropPath());
        wrapper.setTitle(tmdbMovieDetail.getTitle());
        wrapper.setReleaseDate(tmdbMovieDetail.getReleaseDate());
        wrapper.setVoteCount(tmdbMovieDetail.getVoteCount());
        wrapper.setVoteAverage(tmdbMovieDetail.getVoteAverage());

        return wrapper;
    }

    private WatchList findInRealmWatchList(Realm realm, Integer id) {
        return realm.where(WatchList.class).equalTo(FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private MovieItem findInRealmMovieItem(Realm realm, Integer movieId) {

    }

    private Favorite findInRealmFavorite(Realm realm, Integer id) {
        return realm.where(Favorite.class).equalTo(FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private UserRating findInRealmRatings(Realm realm, Integer id) {
        return realm.where(UserRating.class).equalTo(FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private MovieStatus findInRealmMovieStatus(Realm realm, Integer id) {
        return realm.where(MovieStatus.class).equalTo(FIELD_MOVIE_ID, id)
                .findFirst();
    }

    private List<UserList> findInRealmUserLists(Realm realm) {
        return realm.where(UserList.class).findAllAsync();
    }

    private UserList findInRealmUserList(Realm realm, int id) {
        return realm.where(UserList.class).equalTo(FIELD_LIST_ID, id).findFirst();
    }

    private int nextListKey(Realm realm) {
        try {
            Number currentId = realm.where(UserList.class).max(FIELD_LIST_ID);
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

    List<Poster> getMovieImages() {
        return movieImages;
    }

    public void onDestroy() {
        realm.close();
    }
}
