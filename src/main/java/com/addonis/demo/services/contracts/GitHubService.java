package com.addonis.demo.services.contracts;

import com.addonis.demo.models.commitresponse.LastCommitResponse;

import java.io.IOException;
import java.text.ParseException;

public interface GitHubService {

    LastCommitResponse getLastCommit(String url) throws ParseException;
    int getPullsCount(String url) throws IOException;
    int getIssuesCount(String url) throws IOException;
}
