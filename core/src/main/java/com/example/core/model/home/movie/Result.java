
package com.example.core.model.home.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result implements Parcelable
{

    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = new ArrayList<>();
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    public final static Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            Result instance = new Result();
            instance.posterPath = ((String) in.readValue((String.class.getClassLoader())));
            instance.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.overview = ((String) in.readValue((String.class.getClassLoader())));
            instance.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.genreIds, (Integer.class.getClassLoader()));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
            instance.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.backdropPath = ((String) in.readValue((Object.class.getClassLoader())));
            instance.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.video = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    }
    ;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
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

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(posterPath);
        dest.writeValue(adult);
        dest.writeValue(overview);
        dest.writeValue(releaseDate);
        dest.writeList(genreIds);
        dest.writeValue(id);
        dest.writeValue(originalTitle);
        dest.writeValue(originalLanguage);
        dest.writeValue(title);
        dest.writeValue(backdropPath);
        dest.writeValue(popularity);
        dest.writeValue(voteCount);
        dest.writeValue(video);
        dest.writeValue(voteAverage);
    }

    public int describeContents() {
        return  0;
    }

}
