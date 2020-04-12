package com.addonis.demo.utils;

import com.addonis.demo.exceptions.InvalidDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateParser {

    public static java.util.Date parseDate(String value) {
        SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy-MM-dd");
        //2019-01-31        T21:06:18Z
        value = value.substring(0, 10);
        Date date = null;
        try {
            LocalDate ld = LocalDate.parse(value);
            date = SDFormat.parse(value);
        } catch (ParseException e) {
            throw new InvalidDataException("date format");
        }

        return new java.sql.Date(date.getTime());


    }

}
