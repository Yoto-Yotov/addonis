package com.addonis.demo.firstDB.models.commitresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Author
 * Needed for Commit - take commit author
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Author {

    @JsonProperty("date")
    String date;

}
