package com.rambabu.rest.covidtracker.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Countries implements Parcelable {
    private String NewRecovered;

    private String NewDeaths;

    private String TotalRecovered;

    private String TotalConfirmed;

    private String Country;

    private String CountryCode;

    private String Slug;

    private String NewConfirmed;

    private String TotalDeaths;

    private String Date;

    public String getNewRecovered ()
    {
        return NewRecovered;
    }

    public void setNewRecovered (String NewRecovered)
    {
        this.NewRecovered = NewRecovered;
    }

    public String getNewDeaths ()
    {
        return NewDeaths;
    }

    public void setNewDeaths (String NewDeaths)
    {
        this.NewDeaths = NewDeaths;
    }

    public String getTotalRecovered ()
    {
        return TotalRecovered;
    }

    public void setTotalRecovered (String TotalRecovered)
    {
        this.TotalRecovered = TotalRecovered;
    }

    public String getTotalConfirmed ()
    {
        return TotalConfirmed;
    }

    public void setTotalConfirmed (String TotalConfirmed)
    {
        this.TotalConfirmed = TotalConfirmed;
    }

    public String getCountry ()
    {
        return Country;
    }

    public void setCountry (String Country)
    {
        this.Country = Country;
    }

    public String getCountryCode ()
    {
        return CountryCode;
    }

    public void setCountryCode (String CountryCode)
    {
        this.CountryCode = CountryCode;
    }

    public String getSlug ()
    {
        return Slug;
    }

    public void setSlug (String Slug)
    {
        this.Slug = Slug;
    }

    public String getNewConfirmed ()
    {
        return NewConfirmed;
    }

    public void setNewConfirmed (String NewConfirmed)
    {
        this.NewConfirmed = NewConfirmed;
    }

    public String getTotalDeaths ()
    {
        return TotalDeaths;
    }

    public void setTotalDeaths (String TotalDeaths)
    {
        this.TotalDeaths = TotalDeaths;
    }

    public String getDate ()
    {
        return Date;
    }

    public void setDate (String Date)
    {
        this.Date = Date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [NewRecovered = "+NewRecovered+", NewDeaths = "+NewDeaths+", TotalRecovered = "+TotalRecovered+", TotalConfirmed = "+TotalConfirmed+", Country = "+Country+", CountryCode = "+CountryCode+", Slug = "+Slug+", NewConfirmed = "+NewConfirmed+", TotalDeaths = "+TotalDeaths+", Date = "+Date+"]";
    }

    public Countries() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.NewRecovered);
        dest.writeString(this.NewDeaths);
        dest.writeString(this.TotalRecovered);
        dest.writeString(this.TotalConfirmed);
        dest.writeString(this.Country);
        dest.writeString(this.CountryCode);
        dest.writeString(this.Slug);
        dest.writeString(this.NewConfirmed);
        dest.writeString(this.TotalDeaths);
        dest.writeString(this.Date);
    }

    protected Countries(Parcel in) {
        this.NewRecovered = in.readString();
        this.NewDeaths = in.readString();
        this.TotalRecovered = in.readString();
        this.TotalConfirmed = in.readString();
        this.Country = in.readString();
        this.CountryCode = in.readString();
        this.Slug = in.readString();
        this.NewConfirmed = in.readString();
        this.TotalDeaths = in.readString();
        this.Date = in.readString();
    }

    public static final Creator<Countries> CREATOR = new Creator<Countries>() {
        @Override
        public Countries createFromParcel(Parcel source) {
            return new Countries(source);
        }

        @Override
        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    };
}
