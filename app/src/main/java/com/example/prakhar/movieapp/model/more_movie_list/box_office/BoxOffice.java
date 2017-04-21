
package com.example.prakhar.movieapp.model.more_movie_list.box_office;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoxOffice implements Parcelable
{

    @SerializedName("revenue")
    @Expose
    private Double revenue;
    @SerializedName("movie")
    @Expose
    private Movie movie;
    public final static Creator<BoxOffice> CREATOR = new Creator<BoxOffice>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BoxOffice createFromParcel(Parcel in) {
            BoxOffice instance = new BoxOffice();
            instance.revenue = ((Double) in.readValue((Integer.class.getClassLoader())));
            instance.movie = ((Movie) in.readValue((Movie.class.getClassLoader())));
            return instance;
        }

        public BoxOffice[] newArray(int size) {
            return (new BoxOffice[size]);
        }

    }
    ;

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(revenue);
        dest.writeValue(movie);
    }

    public int describeContents() {
        return  0;
    }

}
