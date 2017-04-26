
package com.example.core.model.movie_detail.tmdb;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.core.model.home.movie.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Similar implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("movies")
    @Expose
    private List<Result> movies = new ArrayList<>();
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    public final static Creator<Similar> CREATOR = new Creator<Similar>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Similar createFromParcel(Parcel in) {
            Similar instance = new Similar();
            instance.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.movies, (Result.class.getClassLoader()));
            instance.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Similar[] newArray(int size) {
            return (new Similar[size]);
        }

    }
    ;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Result> getMovies() {
        return movies;
    }

    public void setMovies(List<Result> movies) {
        this.movies = movies;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(movies);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return  0;
    }

}
