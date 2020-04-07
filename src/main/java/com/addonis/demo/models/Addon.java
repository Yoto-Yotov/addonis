package com.addonis.demo.models;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "addons")
@Data
public class Addon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addon_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserInfo userInfo;

    @Column(name = "addon_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "downloads_count")
    private int downloadsCount;

    @Column(name = "origin_link")
    private String originLink;

    @Column(name = "pulls_count")
    private int pullsCount;

    @Column(name = "issues_count")
    private int issuesCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private Set<Tag> tags;

}
