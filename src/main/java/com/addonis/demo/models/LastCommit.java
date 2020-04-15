package com.addonis.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "last_commits")
public class LastCommit {

    @Id
    @Column(name = "last_commit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lastCommitId;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private java.util.Date date;

}
