
package com.example.core.model.home.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("backdrops")
    @Expose
    private List<Backdrop> backdrops = null;
    @SerializedName("posters")
    @Expose
    private List<Poster> posters = null;
    public final static Creator<Images> CREATOR = new Creator<Images>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Images createFromParcel(Parcel in) {
            Images instance = new Images();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.backdrops, (Backdrop.class.getClassLoader()));
            in.readList(instance.posters, (Poster.class.getClassLoader()));
            return instance;
        }

        public Images[] newArray(int size) {
            return (new Images[size]);
        }

    }
    ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(backdrops);
        dest.writeList(posters);
    }

    public int describeContents() {
        return  0;
    }

}
