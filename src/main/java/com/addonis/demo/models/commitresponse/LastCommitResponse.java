package com.addonis.demo.models.commitresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LastCommitResponse {

    @JsonProperty("commit")
    Commit commitObject;

}
