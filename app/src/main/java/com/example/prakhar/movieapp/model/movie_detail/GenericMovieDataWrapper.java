package com.example.prakhar.movieapp.model.movie_detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Prakhar on 4/20/2017.
 */

public class GenericMovieDataWrapper implements Parcelable {

    private Integer movieId;

    private String posterPath;

    private String overview;

    private String releaseDate;

    private String title;

    private String backdropPath;

    private Integer voteCount;

    private Double voteAverage;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.movieId);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.voteAverage);
    }

    public GenericMovieDataWrapper() {
    }

    private GenericMovieDataWrapper(Parcel in) {
        this.movieId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<GenericMovieDataWrapper> CREATOR = new Parcelable.Creator<GenericMovieDataWrapper>() {
        @Override
        public GenericMovieDataWrapper createFromParcel(Parcel source) {
            return new GenericMovieDataWrapper(source);
        }

        @Override
        public GenericMovieDataWrapper[] newArray(int size) {
            return new GenericMovieDataWrapper[size];
        }
    };
}
