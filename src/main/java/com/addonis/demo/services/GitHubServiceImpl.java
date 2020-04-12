package com.addonis.demo.services;

import com.addonis.demo.models.commitresponse.LastCommitResponse;
import com.addonis.demo.models.enums.EPParam;
import com.addonis.demo.services.contracts.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

import static com.addonis.demo.utils.UrlParser.parseUrl;

@Service
public class GitHubServiceImpl implements GitHubService {

    @Override
    public LastCommitResponse getLastCommit(String url) throws ParseException {
        url = parseUrl(url, EPParam.COMMITS);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LastCommitResponse[]> arr = restTemplate.getForEntity(url, LastCommitResponse[].class);
        LastCommitResponse[] array  = arr.getBody();
        LastCommitResponse lastCommitResponse = array[0];

        return lastCommitResponse;
    }

    @Override
    public int getPullCount(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return 0;
    }
}
