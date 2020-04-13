package com.addonis.demo.utils;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.models.commitresponse.LastCommitResponse;

public class LastCommitMapper {

    public static LastCommit mapLastCommitResponseToLastCommit(LastCommitResponse lastCommitResponse) {
        LastCommit lastCommit = new LastCommit();
        java.util.Date lastCommitDate = null;

        lastCommitDate = DateParser.parseDate(lastCommitResponse.getCommitObject().getAuthor().getDate());

        lastCommit.setTitle(lastCommitResponse.getCommitObject().getMessage());
        lastCommit.setDate(lastCommitDate);

        return lastCommit;
    }

}
