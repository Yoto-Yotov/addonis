package com.addonis.demo.models;

import com.addonis.demo.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "addons")
@Where(clause = "status = 'APPROVED'")
@JsonSerialize
public class Addon{

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

    @OneToOne
    @JoinColumn(name = "ide_name")
    private IDE ideId;

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

    @OneToOne
    @JoinColumn(name = "last_commit_id")
    private LastCommit lastCommit;

    @JsonIgnore
    @Lob
    @Column(name = "picture")
    private Byte[] picture;

    @JsonIgnore
    @Lob
    @Column(name = "content")
    private Byte[] content;
}
