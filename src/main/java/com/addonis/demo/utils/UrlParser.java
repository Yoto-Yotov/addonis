package com.addonis.demo.utils;

public class UrlParser {

    public static String parseUrl(String link) {
        String[] url = link.split(".com/");
        return url[1];
    }
}
