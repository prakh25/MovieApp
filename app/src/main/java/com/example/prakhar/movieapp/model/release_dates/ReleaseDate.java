
package com.example.prakhar.movieapp.model.release_dates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReleaseDate implements Parcelable
{

    @SerializedName("certification")
    @Expose
    private String certification;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("type")
    @Expose
    private Integer type;
    public final static Creator<ReleaseDate> CREATOR = new Creator<ReleaseDate>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ReleaseDate createFromParcel(Parcel in) {
            ReleaseDate instance = new ReleaseDate();
            instance.certification = ((String) in.readValue((String.class.getClassLoader())));
            instance.iso6391 = ((String) in.readValue((String.class.getClassLoader())));
            instance.note = ((String) in.readValue((String.class.getClassLoader())));
            instance.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.type = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public ReleaseDate[] newArray(int size) {
            return (new ReleaseDate[size]);
        }

    }
    ;

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(certification);
        dest.writeValue(iso6391);
        dest.writeValue(note);
        dest.writeValue(releaseDate);
        dest.writeValue(type);
    }

    public int describeContents() {
        return  0;
    }

}
