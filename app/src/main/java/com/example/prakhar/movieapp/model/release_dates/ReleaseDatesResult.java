
package com.example.prakhar.movieapp.model.release_dates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReleaseDatesResult implements Parcelable
{

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("release_dates")
    @Expose
    private List<ReleaseDate> releaseDates = null;
    public final static Creator<ReleaseDatesResult> CREATOR = new Creator<ReleaseDatesResult>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ReleaseDatesResult createFromParcel(Parcel in) {
            ReleaseDatesResult instance = new ReleaseDatesResult();
            instance.iso31661 = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.releaseDates, (com.example.prakhar.movieapp.model.release_dates.ReleaseDate.class.getClassLoader()));
            return instance;
        }

        public ReleaseDatesResult[] newArray(int size) {
            return (new ReleaseDatesResult[size]);
        }

    }
    ;

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public List<ReleaseDate> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<ReleaseDate> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iso31661);
        dest.writeList(releaseDates);
    }

    public int describeContents() {
        return  0;
    }

}
