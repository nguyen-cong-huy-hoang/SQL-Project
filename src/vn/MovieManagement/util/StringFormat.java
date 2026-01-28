package vn.MovieManagement.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class StringFormat {

    private StringFormat(){};

    public static boolean stringLimit(int limit, String str) {
        if(str == null) return true;
        if(str.length() > limit) {
            return false;
        }

        return true;
    }

    public static Date toSqlDate(String date) {
        if (date == null) return null;
        try {
            return Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Date parseSqlDate(String dateStr) {
        try {
            return Date.valueOf(dateStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date format (yyyy-MM-dd)", e);
        }
    }


    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date); // yyyy-MM-dd
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
}
