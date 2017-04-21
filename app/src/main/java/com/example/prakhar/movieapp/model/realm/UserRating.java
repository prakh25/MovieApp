package com.example.prakhar.movieapp.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Prakhar on 3/24/2017.
 */

public class UserRating extends RealmObject {

    @PrimaryKey
    private Integer movieId;

    private Integer userRating;

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
