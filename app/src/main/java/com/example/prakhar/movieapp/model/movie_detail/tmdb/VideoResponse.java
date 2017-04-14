
package com.example.prakhar.movieapp.model.movie_detail.tmdb;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Video> results = null;
    public final static Creator<VideoResponse> CREATOR = new Creator<VideoResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public VideoResponse createFromParcel(Parcel in) {
            VideoResponse instance = new VideoResponse();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.results, (Video.class.getClassLoader()));
            return instance;
        }

        public VideoResponse[] newArray(int size) {
            return (new VideoResponse[size]);
        }

    }
    ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
