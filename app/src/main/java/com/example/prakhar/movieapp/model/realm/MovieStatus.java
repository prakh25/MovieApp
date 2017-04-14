package com.example.prakhar.movieapp.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Prakhar on 3/10/2017.
 */

public class MovieStatus extends RealmObject {

    public static final String FIELD_MOVIE_ID = "movieId";

    @PrimaryKey
    private Integer movieId;

    private boolean isAddedToWatchList;

    private boolean isMarkedAsFavorite;

    private boolean isRated;


    public boolean isMarkedAsFavorite() {
        return isMarkedAsFavorite;
    }

    public void setMarkedAsFavorite(boolean markedAsFavorite) {
        isMarkedAsFavorite = markedAsFavorite;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public boolean isAddedToWatchList() {
        return isAddedToWatchList;
    }

    public void setAddedToWatchList(boolean addedToWatchList) {
        isAddedToWatchList = addedToWatchList;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }
}
