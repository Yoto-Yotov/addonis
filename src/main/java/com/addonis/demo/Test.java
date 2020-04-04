package com.addonis.demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException, JSONException {

        Object json = APIUtils.requestDataFromAPI("https://api.github.com", "/repos/stleary/JSON-java/pulls");

        if (json instanceof JSONArray) {
            JSONArray arr = (JSONArray) json;
            for (int i = 0; i < arr.length(); i++) {
//                //long id = j.getLong("id");
//                //JSONObject owner = j.getJSONObject("owner");
//                //System.out.println(id);
                JSONObject j = (JSONObject) arr.get(i);
                System.out.println(j);
            }

            System.out.println("Size = " +  arr.length());
        } else {
            System.out.println(json);
        }


    }
}
