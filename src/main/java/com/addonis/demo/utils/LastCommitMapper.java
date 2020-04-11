package com.addonis.demo.utils;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.models.commitresponse.LastCommitResponse;

import java.text.ParseException;

public class LastCommitMapper {

    public static LastCommit mapLastCommitResponseToLastCommit(LastCommitResponse lastCommitResponse) {
        LastCommit lastCommit = new LastCommit();
        java.util.Date lastCommitDate = null;

        try {
            lastCommitDate = DateParser.parseDate(lastCommitResponse.getCommitObject().getAuthor().getDate());
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Invalid Date Passed!"); //todo change this exception
        }

        lastCommit.setTitle(lastCommitResponse.getCommitObject().getMessage());
        lastCommit.setDate(lastCommitDate);

        return lastCommit;
    }

}
