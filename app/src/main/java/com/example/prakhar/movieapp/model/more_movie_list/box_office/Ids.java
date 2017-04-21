
package com.example.prakhar.movieapp.model.more_movie_list.box_office;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ids implements Parcelable
{

    @SerializedName("trakt")
    @Expose
    private Integer trakt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("imdb")
    @Expose
    private String imdb;
    @SerializedName("tmdb")
    @Expose
    private Integer tmdb;
    public final static Creator<Ids> CREATOR = new Creator<Ids>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Ids createFromParcel(Parcel in) {
            Ids instance = new Ids();
            instance.trakt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.slug = ((String) in.readValue((String.class.getClassLoader())));
            instance.imdb = ((String) in.readValue((String.class.getClassLoader())));
            instance.tmdb = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Ids[] newArray(int size) {
            return (new Ids[size]);
        }

    }
    ;

    public Integer getTrakt() {
        return trakt;
    }

    public void setTrakt(Integer trakt) {
        this.trakt = trakt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Integer getTmdb() {
        return tmdb;
    }

    public void setTmdb(Integer tmdb) {
        this.tmdb = tmdb;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(trakt);
        dest.writeValue(slug);
        dest.writeValue(imdb);
        dest.writeValue(tmdb);
    }

    public int describeContents() {
        return  0;
    }

}
