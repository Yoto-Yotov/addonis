package com.addonis.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateParser {

    public static java.util.Date parseDate(String value) throws ParseException {
        SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy-MM-dd");
        //2019-01-31        T21:06:18Z
        value = value.substring(0, 10);
        LocalDate ld = LocalDate.parse(value);
        java.util.Date date = SDFormat.parse(value);

        return new java.sql.Date(date.getTime());


    }

}
