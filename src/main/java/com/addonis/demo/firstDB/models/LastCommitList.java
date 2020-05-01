package com.addonis.demo.firstDB.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * LastCommitList
 * list with commits
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastCommitList {

    private List<LastCommit> commitList;

    public LastCommitList() {
        this.commitList = new ArrayList<>();
    }
}
