
package com.example.prakhar.movieapp.model.release_dates;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReleaseDateResponse implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<ReleaseDatesResult> results = null;
    public final static Creator<ReleaseDateResponse> CREATOR = new Creator<ReleaseDateResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ReleaseDateResponse createFromParcel(Parcel in) {
            ReleaseDateResponse instance = new ReleaseDateResponse();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.results, (ReleaseDatesResult.class.getClassLoader()));
            return instance;
        }

        public ReleaseDateResponse[] newArray(int size) {
            return (new ReleaseDateResponse[size]);
        }

    }
    ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ReleaseDatesResult> getResults() {
        return results;
    }

    public void setResults(List<ReleaseDatesResult> results) {
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
