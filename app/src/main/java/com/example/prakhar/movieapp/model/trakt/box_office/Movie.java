
package com.example.prakhar.movieapp.model.trakt.box_office;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.prakhar.movieapp.model.trakt.Ids;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("ids")
    @Expose
    private Ids ids;
    public final static Creator<Movie> CREATOR = new Creator<Movie>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Movie createFromParcel(Parcel in) {
            Movie instance = new Movie();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.year = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.ids = ((Ids) in.readValue((Ids.class.getClassLoader())));
            return instance;
        }

        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }

    }
    ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(year);
        dest.writeValue(ids);
    }

    public int describeContents() {
        return  0;
    }

}
