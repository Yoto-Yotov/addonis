package com.addonis.demo.utils;

import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class APIUtils {

    private APIUtils() {}

    public static JSONArray requestDataFromAPI(String uri) throws IOException {
        URL url = new URL(uri);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

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

        String content = sb.toString();

        return new JSONArray(content);
    }
}
