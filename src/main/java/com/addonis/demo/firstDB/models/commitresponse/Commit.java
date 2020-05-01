package com.addonis.demo.firstDB.models.commitresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Commit
 * Needed for LastCommit - author and message of the commit
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Commit {

    @JsonProperty("author")
    Author author;

    @JsonProperty("message")
    String message;

}
