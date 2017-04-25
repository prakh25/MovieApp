
package com.example.core.model.movie_detail.trakt;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distribution implements Parcelable
{

    @SerializedName("1")
    @Expose
    private Integer _1;
    @SerializedName("2")
    @Expose
    private Integer _2;
    @SerializedName("3")
    @Expose
    private Integer _3;
    @SerializedName("4")
    @Expose
    private Integer _4;
    @SerializedName("5")
    @Expose
    private Integer _5;
    @SerializedName("6")
    @Expose
    private Integer _6;
    @SerializedName("7")
    @Expose
    private Integer _7;
    @SerializedName("8")
    @Expose
    private Integer _8;
    @SerializedName("9")
    @Expose
    private Integer _9;
    @SerializedName("10")
    @Expose
    private Integer _10;
    public final static Creator<Distribution> CREATOR = new Creator<Distribution>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Distribution createFromParcel(Parcel in) {
            Distribution instance = new Distribution();
            instance._1 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._2 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._3 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._4 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._5 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._6 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._7 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._8 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._9 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance._10 = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Distribution[] newArray(int size) {
            return (new Distribution[size]);
        }

    }
    ;

    public Integer get1() {
        return _1;
    }

    public void set1(Integer _1) {
        this._1 = _1;
    }

    public Integer get2() {
        return _2;
    }

    public void set2(Integer _2) {
        this._2 = _2;
    }

    public Integer get3() {
        return _3;
    }

    public void set3(Integer _3) {
        this._3 = _3;
    }

    public Integer get4() {
        return _4;
    }

    public void set4(Integer _4) {
        this._4 = _4;
    }

    public Integer get5() {
        return _5;
    }

    public void set5(Integer _5) {
        this._5 = _5;
    }

    public Integer get6() {
        return _6;
    }

    public void set6(Integer _6) {
        this._6 = _6;
    }

    public Integer get7() {
        return _7;
    }

    public void set7(Integer _7) {
        this._7 = _7;
    }

    public Integer get8() {
        return _8;
    }

    public void set8(Integer _8) {
        this._8 = _8;
    }

    public Integer get9() {
        return _9;
    }

    public void set9(Integer _9) {
        this._9 = _9;
    }

    public Integer get10() {
        return _10;
    }

    public void set10(Integer _10) {
        this._10 = _10;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_1);
        dest.writeValue(_2);
        dest.writeValue(_3);
        dest.writeValue(_4);
        dest.writeValue(_5);
        dest.writeValue(_6);
        dest.writeValue(_7);
        dest.writeValue(_8);
        dest.writeValue(_9);
        dest.writeValue(_10);
    }

    public int describeContents() {
        return  0;
    }

}
