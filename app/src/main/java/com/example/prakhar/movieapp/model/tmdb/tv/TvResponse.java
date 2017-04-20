
package com.example.prakhar.movieapp.model.tmdb.tv;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvResponse implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<TvResult> tvResults = null;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    public final static Creator<TvResponse> CREATOR = new Creator<TvResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TvResponse createFromParcel(Parcel in) {
            TvResponse instance = new TvResponse();
            instance.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.tvResults, (TvResult.class.getClassLoader()));
            instance.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public TvResponse[] newArray(int size) {
            return (new TvResponse[size]);
        }

    }
    ;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<TvResult> getTvResults() {
        return tvResults;
    }

    public void setTvResults(List<TvResult> tvResults) {
        this.tvResults = tvResults;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(tvResults);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
    }

    public int describeContents() {
        return  0;
    }

}
