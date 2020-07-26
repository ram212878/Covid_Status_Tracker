package com.rambabu.rest.covidtracker.helper.timelinehelper;

public class TimeLine
{
    private String country;

    private String recovered;

    private String cases;

    private String last_update;

    private String deaths;

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getRecovered ()
    {
        return recovered;
    }

    public void setRecovered (String recovered)
    {
        this.recovered = recovered;
    }

    public String getCases ()
    {
        return cases;
    }

    public void setCases (String cases)
    {
        this.cases = cases;
    }

    public String getLast_update ()
    {
        return last_update;
    }

    public void setLast_update (String last_update)
    {
        this.last_update = last_update;
    }

    public String getDeaths ()
    {
        return deaths;
    }

    public void setDeaths (String deaths)
    {
        this.deaths = deaths;
    }

    @Override
    public String toString()
    {
        return "class TimeLine [country = "+country+", recovered = "+recovered+", cases = "+cases+", last_update = "+last_update+", deaths = "+deaths+"]";
    }
}