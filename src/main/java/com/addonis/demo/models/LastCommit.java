package com.addonis.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "last_commits")
@Data
public class LastCommit {

    @Id
    @Column(name = "last_commit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lastCommitId;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private java.util.Date date;

    @OneToOne
    @JoinColumn(name = "addon_id")
    private Addon addon;
}
