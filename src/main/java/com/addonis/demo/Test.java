package com.addonis.demo;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.models.commitresponse.LastCommitResponse;
import com.addonis.demo.services.GitHubServiceImpl;
import com.addonis.demo.services.contracts.GitHubService;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

import static com.addonis.demo.api.LastCommitMapper.mapLastCommitResponseToLastCommit;

public class Test {

    public static void main(String[] args) throws IOException, JSONException, ParseException {

        GitHubService githubService = new GitHubServiceImpl();

        LastCommitResponse lastCommitTest = githubService.
                getLastCommit("https://api.github.com/repos/YotoBG/projectjs-event-organizer/commits");
        System.out.println(lastCommitTest.getCommitObject().getMessage());
        System.out.println(lastCommitTest.getCommitObject().getAuthor().getDate());

        LastCommit lastCommit = mapLastCommitResponseToLastCommit(lastCommitTest);
        System.out.println(lastCommit);

        System.out.println(lastCommit.getDate());
        System.out.println(lastCommit.getTitle());


//        Object json = APIUtils.requestDataFromAPI("https://api.github.com/repos/" +
//                UrlParser.parseUrl("https://github.com/YotoBG/projectjs-event-organizer") + "/commits");
            //https://github.com/YotoBG/projectjs-event-organizer

//        if (json instanceof JSONArray) {
//            JSONArray arr = (JSONArray) json;
          //  for (int i = 0; i < arr.length(); i++) {
//                //long id = j.getLong("id");
//                //JSONObject owner = j.getJSONObject("owner");
//                //System.out.println(id);
              //  JSONObject j = (JSONObject) arr.get(0);
//                System.out.println(j);
            //}
//            JSONObject j = (JSONObject) arr.get(0);
           // System.out.println(j);
//            System.out.println(j.getJSONObject("commit").getString("message").replaceAll("\n", " "));
//            System.out.println(j.getJSONObject("commit").getJSONObject("author").getString("date"));
//            System.out.println("Size = " +  arr.length());

//        } else {
//            System.out.println(json);
//        }


    }
}
