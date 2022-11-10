package fpt.edu.aptcoffee.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class XDate {
    static SimpleDateFormat spfDate = new SimpleDateFormat("dd-MM-yyyy");
    static SimpleDateFormat spfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static Date toDate(String date) throws ParseException {
        return spfDate.parse(date);
    }

    public static String toStringDate(Date date){
        return spfDate.format(date);
    }

    public static Date toDateTime(String dateTime) throws ParseException {
        return spfDateTime.parse(dateTime);
    }

    public static String toStringDateTime(Date dateTime){
        return spfDateTime.format(dateTime);
    }
}
