package com.addonis.demo.firstDB.models;

import com.addonis.demo.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

/**
 * Addon  - this is the main product. Has name, description, IDE, origin link (GitHub). The other informaton is taken from GIT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "addons", schema = "addonis_db")
@Where(clause = "enabled = 1")
@JsonSerialize
public class Addon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addon_id")
    private int id;

    @Column(name = "enabled")
    private Integer enabled = 1;

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
    @JoinColumn(name = "ide_id")
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

    @Column(name = "content")
    private int binaryFile;

    @Column(name = "readme")
    private int readmeId;
}
