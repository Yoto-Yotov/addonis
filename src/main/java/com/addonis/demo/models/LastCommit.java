package com.addonis.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("massage")
    @Column(name = "title")
    private String title;

    @JsonProperty("date")
    @Column(name = "date")
    private java.util.Date date;

}
