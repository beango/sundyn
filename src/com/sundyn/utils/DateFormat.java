package com.sundyn.utils;

import java.text.*;
import java.util.*;

public class DateFormat
{
    public static String dateFormat(final Date date) {
        final String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    public static Date getDate(final String date) throws ParseException {
        final String format = "yyyy-MM-dd";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }
    
    public static boolean isToday(final String date1) throws ParseException {
        final Date d = getDate(date1);
        final Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(5, 1);
        final Calendar c2 = Calendar.getInstance();
        c2.setTime(d);
        final long now = new Date().getTime();
        return now < c.getTime().getTime() && now > c2.getTime().getTime();
    }
    
    public static void main(final String[] args) throws ParseException {
    }
}
