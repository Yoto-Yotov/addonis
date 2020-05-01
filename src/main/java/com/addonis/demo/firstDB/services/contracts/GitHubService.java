package com.addonis.demo.firstDB.services.contracts;

import com.addonis.demo.firstDB.models.Readme;
import com.addonis.demo.firstDB.models.commitresponse.LastCommitResponse;

import java.io.IOException;

public interface GitHubService {

    LastCommitResponse getLastCommit(String url);
    int getPullsCount(String url);
    int getIssuesCount(String url);
    Readme getReadme(String url) throws IOException;
}
