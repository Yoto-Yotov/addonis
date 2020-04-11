package com.addonis.demo;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.utils.APIUtils;
import com.addonis.demo.utils.UrlParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class Test {

    public static void main(String[] args) throws IOException, JSONException {


        Object json = APIUtils.requestDataFromAPI("https://api.github.com/repos/", "/YotoBG/projectjs-event-organizer" + "/commits");
            //https://github.com/YotoBG/projectjs-event-organizer
        System.out.println(UrlParser.parseUrl("https://github.com/YotoBG/projectjs-event-organizer"));
        if (json instanceof JSONArray) {
            JSONArray arr = (JSONArray) json;
          //  for (int i = 0; i < arr.length(); i++) {
//                //long id = j.getLong("id");
//                //JSONObject owner = j.getJSONObject("owner");
//                //System.out.println(id);
                JSONObject j = (JSONObject) arr.get(0);
//                System.out.println(j);
            //}
            //JSONObject j = (JSONObject) arr.get(0);
//            System.out.println(j);
            System.out.println(j.getJSONObject("commit").getString("message").replaceAll("\n", " "));
            System.out.println(j.getJSONObject("commit").getJSONObject("author").getString("date"));
            System.out.println("Size = " +  arr.length());

        } else {
            System.out.println(json);
        }


    }
}
