package com.example.core.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Prakhar on 3/10/2017.
 */

public class MovieStatus extends RealmObject {

    @PrimaryKey
    private Integer movieId;

    private boolean isAddedToWatchList;

    private boolean isMarkedAsFavorite;

    private boolean isRated;

    private boolean isPresentInUserList;


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

    public boolean isPresentInUserList() {
        return isPresentInUserList;
    }

    public void setPresentInUserList(boolean presentInUserList) {
        isPresentInUserList = presentInUserList;
    }
}
