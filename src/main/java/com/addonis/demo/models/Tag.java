package com.addonis.demo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tags")
//@Entity
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
}
