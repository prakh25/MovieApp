
package com.example.prakhar.movieapp.model.movie_detail.trakt;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TraktMovieRating implements Parcelable
{

    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("votes")
    @Expose
    private Integer votes;
    @SerializedName("distribution")
    @Expose
    private Distribution distribution;
    public final static Creator<TraktMovieRating> CREATOR = new Creator<TraktMovieRating>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TraktMovieRating createFromParcel(Parcel in) {
            TraktMovieRating instance = new TraktMovieRating();
            instance.rating = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.votes = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.distribution = ((Distribution) in.readValue((Distribution.class.getClassLoader())));
            return instance;
        }

        public TraktMovieRating[] newArray(int size) {
            return (new TraktMovieRating[size]);
        }

    }
    ;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rating);
        dest.writeValue(votes);
        dest.writeValue(distribution);
    }

    public int describeContents() {
        return  0;
    }

}
