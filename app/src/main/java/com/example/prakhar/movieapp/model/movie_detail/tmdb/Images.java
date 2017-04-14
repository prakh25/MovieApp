
package com.example.prakhar.movieapp.model.movie_detail.tmdb;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.prakhar.movieapp.model.tmdb.Backdrop;
import com.example.prakhar.movieapp.model.tmdb.Poster;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images implements Parcelable
{

    @SerializedName("backdrops")
    @Expose
    private List<Poster> backdrops = null;
    @SerializedName("posters")
    @Expose
    private List<Poster> posters = null;
    public final static Creator<Images> CREATOR = new Creator<Images>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Images createFromParcel(Parcel in) {
            Images instance = new Images();
            in.readList(instance.backdrops, (Backdrop.class.getClassLoader()));
            in.readList(instance.posters, (Poster.class.getClassLoader()));
            return instance;
        }

        public Images[] newArray(int size) {
            return (new Images[size]);
        }

    }
    ;

    public List<Poster> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Poster> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(backdrops);
        dest.writeList(posters);
    }

    public int describeContents() {
        return  0;
    }

}
