package com.addonis.demo.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * APIUtils is used for connection to GitHub.
 * Takes string and returns JSON array used for getting addon information.
 */
public final class APIUtils {

    private APIUtils() {}

    public static JSONArray requestDataFromAPI(String uri) throws IOException {
        return new JSONArray(getContent(uri));
    }

    public static JSONObject requestDataObject(String uri) throws IOException {
        return new JSONObject(getContent(uri));
    }

    private static String getContent(String uri) throws IOException {
        URL url1 = new URL(uri);
        HttpsURLConnection connection = (HttpsURLConnection) url1.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String input = null;
            while ((input = br.readLine()) != null){
                sb.append(input);
            }
        }

        return sb.toString();
    }
}
