package com.addonis.demo.services;

import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.commitresponse.LastCommitResponse;
import com.addonis.demo.models.enums.EPParam;
import com.addonis.demo.services.contracts.GitHubService;
import com.addonis.demo.utils.APIUtils;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.addonis.demo.utils.UrlParser.parseUrl;

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
            throw new InvalidDataException("url");
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
            throw new InvalidDataException("url");
        }
        return jsonArr.length();
    }
}
