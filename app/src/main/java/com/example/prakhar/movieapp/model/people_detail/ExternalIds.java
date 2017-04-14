
package com.example.prakhar.movieapp.model.people_detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalIds implements Parcelable
{

    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("freebase_mid")
    @Expose
    private String freebaseMid;
    @SerializedName("freebase_id")
    @Expose
    private Object freebaseId;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("instagram_id")
    @Expose
    private String instagramId;
    @SerializedName("tvrage_id")
    @Expose
    private Integer tvrageId;
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;
    public final static Creator<ExternalIds> CREATOR = new Creator<ExternalIds>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ExternalIds createFromParcel(Parcel in) {
            ExternalIds instance = new ExternalIds();
            instance.facebookId = ((String) in.readValue((String.class.getClassLoader())));
            instance.freebaseMid = ((String) in.readValue((String.class.getClassLoader())));
            instance.freebaseId = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.imdbId = ((String) in.readValue((String.class.getClassLoader())));
            instance.instagramId = ((String) in.readValue((String.class.getClassLoader())));
            instance.tvrageId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.twitterId = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ExternalIds[] newArray(int size) {
            return (new ExternalIds[size]);
        }

    }
    ;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFreebaseMid() {
        return freebaseMid;
    }

    public void setFreebaseMid(String freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    public Object getFreebaseId() {
        return freebaseId;
    }

    public void setFreebaseId(Object freebaseId) {
        this.freebaseId = freebaseId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getInstagramId() {
        return instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public Integer getTvrageId() {
        return tvrageId;
    }

    public void setTvrageId(Integer tvrageId) {
        this.tvrageId = tvrageId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(facebookId);
        dest.writeValue(freebaseMid);
        dest.writeValue(freebaseId);
        dest.writeValue(imdbId);
        dest.writeValue(instagramId);
        dest.writeValue(tvrageId);
        dest.writeValue(twitterId);
    }

    public int describeContents() {
        return  0;
    }

}
