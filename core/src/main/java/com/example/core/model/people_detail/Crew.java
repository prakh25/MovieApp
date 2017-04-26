
package com.example.core.model.people_detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crew implements Parcelable
{

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("poster_path")
    @Expose
    private Object posterPath;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    public final static Creator<Crew> CREATOR = new Creator<Crew>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Crew createFromParcel(Parcel in) {
            Crew instance = new Crew();
            instance.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.creditId = ((String) in.readValue((String.class.getClassLoader())));
            instance.department = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.job = ((String) in.readValue((String.class.getClassLoader())));
            instance.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
            instance.posterPath = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.mediaType = ((String) in.readValue((String.class.getClassLoader())));
            instance.episodeCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.originalName = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Crew[] newArray(int size) {
            return (new Crew[size]);
        }

    }
    ;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Object getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(Object posterPath) {
        this.posterPath = posterPath;
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(adult);
        dest.writeValue(creditId);
        dest.writeValue(department);
        dest.writeValue(id);
        dest.writeValue(job);
        dest.writeValue(originalTitle);
        dest.writeValue(posterPath);
        dest.writeValue(releaseDate);
        dest.writeValue(title);
        dest.writeValue(mediaType);
        dest.writeValue(episodeCount);
        dest.writeValue(firstAirDate);
        dest.writeValue(name);
        dest.writeValue(originalName);
    }

    public int describeContents() {
        return  0;
    }

}
