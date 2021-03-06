package com.addonis.demo.firstDB.services;

import com.addonis.demo.constants.Constants;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.firstDB.models.Readme;
import com.addonis.demo.firstDB.models.commitresponse.LastCommitResponse;
import com.addonis.demo.firstDB.services.contracts.GitHubService;
import com.addonis.demo.models.enums.EPParam;
import com.addonis.demo.utils.APIUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.addonis.demo.url.UrlParser.parseUrl;

/**
 * GitHubServiceImpl is a class used for taking the information about the addon from gitHub.
 */
@Service
public class GitHubServiceImpl implements GitHubService {

    @Override
    public LastCommitResponse getLastCommit(String url) {
        url = parseUrl(url, EPParam.COMMITS);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LastCommitResponse[]> arr = restTemplate.getForEntity(url, LastCommitResponse[].class);
        LastCommitResponse[] array  = arr.getBody();
        LastCommitResponse lastCommitResponse = array[0];

        return lastCommitResponse;
    }

    @Override
    public int getPullsCount(String url) {
        url = parseUrl(url, EPParam.PULLS);
        JSONArray jsonArr = null;
        try {
            jsonArr = APIUtils.requestDataFromAPI(url);
        } catch (IOException e) {
            throw new InvalidDataException(Constants.URL);
        }
        return jsonArr.length();
    }

    @Override
    public int getIssuesCount(String url) {
        url = parseUrl(url, EPParam.ISSUES);
        JSONArray jsonArr = null;
        try {
            jsonArr = APIUtils.requestDataFromAPI(url);
        } catch (IOException e) {
            throw new InvalidDataException(Constants.URL);
        }
        return jsonArr.length();
    }

    @Override
    public Readme getReadme(String url) throws IOException {
        url = parseUrl(url, EPParam.README);
        JSONObject obj = null;
        try {
            obj = APIUtils.requestDataObject(url);
        } catch (IOException e) {
            throw new InvalidDataException("url");
        }
        Readme readme = new Readme();
        readme.setText(obj.getString("content").getBytes());
        return readme;
    }
}
