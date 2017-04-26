
package com.example.core.model.people_detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleDetails implements Parcelable
{

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("also_known_as")
    @Expose
    private List<String> alsoKnownAs = null;
    @SerializedName("biography")
    @Expose
    private String biography;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("deathday")
    @Expose
    private String deathday;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("place_of_birth")
    @Expose
    private String placeOfBirth;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("combined_credits")
    @Expose
    private CombinedCredits combinedCredits;
    @SerializedName("external_ids")
    @Expose
    private ExternalIds externalIds;
    public final static Creator<PeopleDetails> CREATOR = new Creator<PeopleDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PeopleDetails createFromParcel(Parcel in) {
            PeopleDetails instance = new PeopleDetails();
            instance.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            in.readList(instance.alsoKnownAs, (String.class.getClassLoader()));
            instance.biography = ((String) in.readValue((String.class.getClassLoader())));
            instance.birthday = ((String) in.readValue((String.class.getClassLoader())));
            instance.deathday = ((String) in.readValue((String.class.getClassLoader())));
            instance.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.homepage = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.imdbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.placeOfBirth = ((String) in.readValue((String.class.getClassLoader())));
            instance.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.profilePath = ((String) in.readValue((String.class.getClassLoader())));
            instance.combinedCredits = ((CombinedCredits) in.readValue((CombinedCredits.class.getClassLoader())));
            instance.externalIds = ((ExternalIds) in.readValue((ExternalIds.class.getClassLoader())));
            return instance;
        }

        public PeopleDetails[] newArray(int size) {
            return (new PeopleDetails[size]);
        }

    }
    ;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public CombinedCredits getCombinedCredits() {
        return combinedCredits;
    }

    public void setCombinedCredits(CombinedCredits combinedCredits) {
        this.combinedCredits = combinedCredits;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(adult);
        dest.writeList(alsoKnownAs);
        dest.writeValue(biography);
        dest.writeValue(birthday);
        dest.writeValue(deathday);
        dest.writeValue(gender);
        dest.writeValue(homepage);
        dest.writeValue(id);
        dest.writeValue(imdbId);
        dest.writeValue(name);
        dest.writeValue(placeOfBirth);
        dest.writeValue(popularity);
        dest.writeValue(profilePath);
        dest.writeValue(combinedCredits);
        dest.writeValue(externalIds);
    }

    public int describeContents() {
        return  0;
    }

    public ExternalIds getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(ExternalIds externalIds) {
        this.externalIds = externalIds;
    }
}
