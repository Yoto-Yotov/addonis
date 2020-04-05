package com.addonis.demo;

import com.addonis.demo.utils.APIUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException, JSONException {

        Object json = APIUtils.requestDataFromAPI("https://api.github.com", "/repos/stleary/JSON-java/commits");

        if (json instanceof JSONArray) {
            JSONArray arr = (JSONArray) json;
            for (int i = 0; i < arr.length(); i++) {
//                //long id = j.getLong("id");
//                //JSONObject owner = j.getJSONObject("owner");
//                //System.out.println(id);
                JSONObject j = (JSONObject) arr.get(i);
                System.out.println(j);
            }
            JSONObject j = (JSONObject) arr.get(0);
            System.out.println(j);
            System.out.println(j.getJSONObject("commit").getString("message").replaceAll("\n", " "));
            System.out.println(j.getJSONObject("commit").getJSONObject("author").getString("date"));
            System.out.println("Size = " +  arr.length());
        } else {
            System.out.println(json);
        }


    }
}
