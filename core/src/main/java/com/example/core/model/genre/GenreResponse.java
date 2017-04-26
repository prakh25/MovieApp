
package com.example.core.model.genre;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse implements Parcelable
{

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    public final static Creator<GenreResponse> CREATOR = new Creator<GenreResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GenreResponse createFromParcel(Parcel in) {
            GenreResponse instance = new GenreResponse();
            in.readList(instance.genres, (Genre.class.getClassLoader()));
            return instance;
        }

        public GenreResponse[] newArray(int size) {
            return (new GenreResponse[size]);
        }

    }
    ;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(genres);
    }

    public int describeContents() {
        return  0;
    }

}
