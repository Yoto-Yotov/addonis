package com.addonis.demo.utils;

import com.addonis.demo.models.enums.EPParam;

/**
 * UrlParser
 * Parses the addon link provided from the user and rebuilds it depending on our needs using the EPParam (enumerator)
 */
public class UrlParser {

    final static String GIT_HUB_LINK = "https://api.github.com/repos/";
    final static String GIT_HUB_README = "https://raw.githubusercontent.com/";

    public static String parseUrl(String link, EPParam param) {
        String[] url = link.split(".com/");
        StringBuilder sb = new StringBuilder();
        sb.append(GIT_HUB_LINK);
        sb.append(url[1]);
        sb.append("/");
        sb.append(param.getParam());
        return sb.toString();
    }

    public static String parseUrlReadme(String link, EPParam param) {
        String[] url = link.split("/");
        StringBuilder sb = new StringBuilder();
        sb.append(GIT_HUB_README);
        sb.append(url[3]);
        sb.append("/");
        sb.append(url[4]);
        sb.append(param.getParam());
        return sb.toString();
    }
}
