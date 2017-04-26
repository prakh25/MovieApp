
package com.example.core.model.person_search;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonSearchResult implements Parcelable
{

    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("known_for")
    @Expose
    private List<KnownFor> knownFor = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    public final static Creator<PersonSearchResult> CREATOR = new Creator<PersonSearchResult>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PersonSearchResult createFromParcel(Parcel in) {
            PersonSearchResult instance = new PersonSearchResult();
            instance.profilePath = ((String) in.readValue((Object.class.getClassLoader())));
            instance.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.knownFor, (KnownFor.class.getClassLoader()));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public PersonSearchResult[] newArray(int size) {
            return (new PersonSearchResult[size]);
        }

    }
    ;

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<KnownFor> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<KnownFor> knownFor) {
        this.knownFor = knownFor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(profilePath);
        dest.writeValue(adult);
        dest.writeValue(id);
        dest.writeList(knownFor);
        dest.writeValue(name);
        dest.writeValue(popularity);
    }

    public int describeContents() {
        return  0;
    }

}
