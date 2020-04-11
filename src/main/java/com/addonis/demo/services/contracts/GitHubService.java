package com.addonis.demo.services.contracts;

import com.addonis.demo.models.commitresponse.LastCommitResponse;

import java.text.ParseException;

public interface GitHubService {

    public LastCommitResponse getLastCommit(String url) throws ParseException;
}
