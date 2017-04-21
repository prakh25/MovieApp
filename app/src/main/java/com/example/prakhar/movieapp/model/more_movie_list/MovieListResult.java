package com.example.prakhar.movieapp.model.more_movie_list;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.prakhar.movieapp.model.home.movie.Result;

/**
 * Created by Prakhar on 3/14/2017.
 */

public class MovieListResult implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.result, flags);
        dest.writeValue(this.revenue);
        dest.writeByte(this.isAddedToWatchlist ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isMarkedAsFavorite ? (byte) 1 : (byte) 0);
        dest.writeInt(this.userRating);
    }

    public MovieListResult() {
    }

    private MovieListResult(Parcel in) {
        this.result = in.readParcelable(Result.class.getClassLoader());
        this.revenue = (Double) in.readValue(Double.class.getClassLoader());
        this.isAddedToWatchlist = in.readByte() != 0;
        this.isMarkedAsFavorite = in.readByte() != 0;
        this.userRating = in.readInt();
    }

    public static final Parcelable.Creator<MovieListResult> CREATOR = new Parcelable.Creator<MovieListResult>() {
        @Override
        public MovieListResult createFromParcel(Parcel source) {
            return new MovieListResult(source);
        }

        @Override
        public MovieListResult[] newArray(int size) {
            return new MovieListResult[size];
        }
    };
}
