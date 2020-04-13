package com.addonis.demo.services.contracts;

import com.addonis.demo.models.commitresponse.LastCommitResponse;

public interface GitHubService {

    LastCommitResponse getLastCommit(String url);
    int getPullsCount(String url);
    int getIssuesCount(String url);
}
