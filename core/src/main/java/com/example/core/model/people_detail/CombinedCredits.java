
package com.example.core.model.people_detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CombinedCredits implements Parcelable
{

    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    public final static Creator<CombinedCredits> CREATOR = new Creator<CombinedCredits>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CombinedCredits createFromParcel(Parcel in) {
            CombinedCredits instance = new CombinedCredits();
            in.readList(instance.cast, (Cast.class.getClassLoader()));
            in.readList(instance.crew, (com.example.core.model.people_detail.Crew.class.getClassLoader()));
            return instance;
        }

        public CombinedCredits[] newArray(int size) {
            return (new CombinedCredits[size]);
        }

    }
    ;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return  0;
    }

}
