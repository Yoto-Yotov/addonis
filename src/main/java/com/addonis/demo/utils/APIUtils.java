package com.addonis.demo.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public final class APIUtils {

    private APIUtils() {}

    public static Object requestDataFromAPI(String endpoint, String uri) throws IOException {
        URL url = new URL(endpoint + uri);
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
        if (content.startsWith("[")) {
            return new JSONArray(content);
        }

        return new JSONObject(content);
    }
}
