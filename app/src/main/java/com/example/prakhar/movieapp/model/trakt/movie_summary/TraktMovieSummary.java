
package com.example.prakhar.movieapp.model.trakt.movie_summary;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.prakhar.movieapp.model.trakt.Ids;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TraktMovieSummary implements Parcelable
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
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("released")
    @Expose
    private String released;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("votes")
    @Expose
    private Integer votes;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("available_translations")
    @Expose
    private List<String> availableTranslations = null;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("certification")
    @Expose
    private String certification;
    public final static Creator<TraktMovieSummary> CREATOR = new Creator<TraktMovieSummary>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TraktMovieSummary createFromParcel(Parcel in) {
            TraktMovieSummary instance = new TraktMovieSummary();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.year = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.ids = ((Ids) in.readValue((Ids.class.getClassLoader())));
            instance.tagline = ((String) in.readValue((String.class.getClassLoader())));
            instance.overview = ((String) in.readValue((String.class.getClassLoader())));
            instance.released = ((String) in.readValue((String.class.getClassLoader())));
            instance.runtime = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.trailer = ((String) in.readValue((String.class.getClassLoader())));
            instance.homepage = ((String) in.readValue((String.class.getClassLoader())));
            instance.rating = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.votes = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
            instance.language = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.availableTranslations, (String.class.getClassLoader()));
            in.readList(instance.genres, (String.class.getClassLoader()));
            instance.certification = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public TraktMovieSummary[] newArray(int size) {
            return (new TraktMovieSummary[size]);
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

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getAvailableTranslations() {
        return availableTranslations;
    }

    public void setAvailableTranslations(List<String> availableTranslations) {
        this.availableTranslations = availableTranslations;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(year);
        dest.writeValue(ids);
        dest.writeValue(tagline);
        dest.writeValue(overview);
        dest.writeValue(released);
        dest.writeValue(runtime);
        dest.writeValue(trailer);
        dest.writeValue(homepage);
        dest.writeValue(rating);
        dest.writeValue(votes);
        dest.writeValue(updatedAt);
        dest.writeValue(language);
        dest.writeList(availableTranslations);
        dest.writeList(genres);
        dest.writeValue(certification);
    }

    public int describeContents() {
        return  0;
    }

}
