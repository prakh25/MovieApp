package com.example.prakhar.movieapp.model.more;

import com.example.prakhar.movieapp.model.tmdb.Result;

/**
 * Created by Prakhar on 3/14/2017.
 */

public class MoreListResult {

    private Result result;
    private Double revenue = 0.0;
    private boolean isAddedToWatchlist;
    private boolean isMarkedAsFavorite;
    private int userRating;


    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public boolean isAddedToWatchlist() {
        return isAddedToWatchlist;
    }

    public void setAddedToWatchlist(boolean addedToWatchlist) {
        isAddedToWatchlist = addedToWatchlist;
    }

    public boolean isMarkedAsFavorite() {
        return isMarkedAsFavorite;
    }

    public void setMarkedAsFavorite(boolean markedAsFavorite) {
        isMarkedAsFavorite = markedAsFavorite;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
}
