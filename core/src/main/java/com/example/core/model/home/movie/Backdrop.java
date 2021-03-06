
package com.example.core.model.home.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Backdrop implements Parcelable
{

    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("iso_639_1")
    @Expose
    private Object iso6391;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("width")
    @Expose
    private Integer width;
    public final static Creator<Backdrop> CREATOR = new Creator<Backdrop>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Backdrop createFromParcel(Parcel in) {
            Backdrop instance = new Backdrop();
            instance.aspectRatio = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.filePath = ((String) in.readValue((String.class.getClassLoader())));
            instance.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.iso6391 = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.voteAverage = ((Double) in.readValue((Integer.class.getClassLoader())));
            instance.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Backdrop[] newArray(int size) {
            return (new Backdrop[size]);
        }

    }
    ;

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Object getIso6391() {
        return iso6391;
    }

    public void setIso6391(Object iso6391) {
        this.iso6391 = iso6391;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(aspectRatio);
        dest.writeValue(filePath);
        dest.writeValue(height);
        dest.writeValue(iso6391);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(width);
    }

    public int describeContents() {
        return  0;
    }

}
