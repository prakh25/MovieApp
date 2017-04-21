package com.example.prakhar.movieapp.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Prakhar on 3/25/2017.
 */

public class WatchList extends RealmObject {

    public static final String FIELD_MOVIE_ID = "movieId";

    @PrimaryKey
    private Integer movieId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
