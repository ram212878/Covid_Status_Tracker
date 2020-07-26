package com.rambabu.rest.covidtracker.helper;

import java.text.DecimalFormat;

public class FormatNumber {
    public String getFormattedNumber(String s){
        Long number = Long.parseLong(s);
        DecimalFormat df= new DecimalFormat("##,##,###");
        return df.format(number);

    }
}
