
package com.example.prakhar.movieapp.model.movie_detail.tmdb;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credits implements Parcelable
{

    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    public final static Creator<Credits> CREATOR = new Creator<Credits>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Credits createFromParcel(Parcel in) {
            Credits instance = new Credits();
            in.readList(instance.cast, (Cast.class.getClassLoader()));
            in.readList(instance.crew, (com.example.prakhar.movieapp.model.movie_detail.tmdb.Crew.class.getClassLoader()));
            return instance;
        }

        public Credits[] newArray(int size) {
            return (new Credits[size]);
        }

    }
    ;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return  0;
    }

}
