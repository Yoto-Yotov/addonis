package com.addonis.demo.services.contracts;

import com.addonis.demo.models.LastCommit;

public interface GitHubService {

    public LastCommit getLastCommit(String url);
}
