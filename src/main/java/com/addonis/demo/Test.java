package com.addonis.demo;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.services.GitHubServiceImpl;
import com.addonis.demo.services.contracts.GitHubService;
import com.addonis.demo.utils.APIUtils;
import com.addonis.demo.utils.UrlParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class Test {

    public static void main(String[] args) throws IOException, JSONException {


        GitHubService githubService = new GitHubServiceImpl();

        LastCommit lastCommitTest = githubService.getLastCommit("https://api.github.com/repos/YotoBG/projectjs-event-organizer/commits");
        System.out.println(lastCommitTest.getDate());
        System.out.println(lastCommitTest.getTitle());

//        JsonNode lastCommitNode = new ObjectMapper().readTree()

        Object json = APIUtils.requestDataFromAPI("https://api.github.com/repos/", UrlParser.parseUrl("https://github.com/YotoBG/projectjs-event-organizer") + "/commits");
            //https://github.com/YotoBG/projectjs-event-organizer

        if (json instanceof JSONArray) {
            JSONArray arr = (JSONArray) json;
          //  for (int i = 0; i < arr.length(); i++) {
//                //long id = j.getLong("id");
//                //JSONObject owner = j.getJSONObject("owner");
//                //System.out.println(id);
              //  JSONObject j = (JSONObject) arr.get(0);
//                System.out.println(j);
            //}
            JSONObject j = (JSONObject) arr.get(0);
           // System.out.println(j);
//            System.out.println(j.getJSONObject("commit").getString("message").replaceAll("\n", " "));
//            System.out.println(j.getJSONObject("commit").getJSONObject("author").getString("date"));
//            System.out.println("Size = " +  arr.length());

        } else {
            System.out.println(json);
        }


    }
}
