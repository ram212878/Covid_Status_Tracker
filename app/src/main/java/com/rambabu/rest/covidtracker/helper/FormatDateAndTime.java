package com.rambabu.rest.covidtracker.helper;

public class FormatDateAndTime {
    private final String DATE;

    public FormatDateAndTime(String date) {
        this.DATE = date;
    }

    public String getDate() {
        String date = DATE.substring(0, DATE.indexOf('T'));
        String[] revDate = date.split("-");
        date = revDate[2]+"-"+revDate[1]+"-"+revDate[0];
        return date;
    }

    public String getTime() {
        return DATE.substring(DATE.indexOf('T') + 1,DATE.lastIndexOf(":"));
    }
}
