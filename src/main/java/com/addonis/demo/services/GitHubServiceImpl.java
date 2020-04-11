package com.addonis.demo.services;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.models.LastCommitList;
import com.addonis.demo.services.contracts.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GitHubServiceImpl implements GitHubService {

    @Override
    public LastCommit getLastCommit(String url) {
        RestTemplate restTemplate = new RestTemplate();
        LastCommitList response = restTemplate.getForObject(url, LastCommitList.class);
        List<LastCommit> commits = response.getCommitList();
        return commits.get(0);




//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<LastCommit[]> arr = restTemplate.getForEntity(url, LastCommit[].class);
//        LastCommit[] array  = arr.getBody();
//        return array[0];
    }
}
