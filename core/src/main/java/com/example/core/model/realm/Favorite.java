package com.example.core.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Prakhar on 3/25/2017.
 */

public class Favorite extends RealmObject {

    @PrimaryKey
    private Integer movieId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

}
