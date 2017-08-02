package com.paul.shelton.measureit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ecom-shelton.paul on 23/07/17.
 */
public class DateUtility {
    public static Date getDate(String dateStr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDate(String dateStr) {
        return getDate(dateStr, "yyyy-MM-dd hh:mm:ss");
    }
}
