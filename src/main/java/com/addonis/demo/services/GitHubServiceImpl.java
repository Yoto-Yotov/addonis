package com.addonis.demo.services;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.services.contracts.GitHubService;
import org.springframework.web.client.RestTemplate;

public class GitHubServiceImpl implements GitHubService {

    @Override
    public LastCommit getLastCommit(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, LastCommit.class);
    }
}
