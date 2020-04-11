package com.addonis.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "last_commits")
public class LastCommit {

    @Id
    @Column(name = "last_commit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lastCommitId;

    @JsonProperty("message")
    @JsonUnwrapped
    @Column(name = "title")
    private String title;

    @JsonUnwrapped
    @JsonProperty("date")
    @Column(name = "date")
    private java.util.Date date;

}
