package com.addonis.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Tag is a class that represents the tags. Addons have Set of Tags.
 * Each tag has name and can be added from registered user or admin to addon.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "tags")
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Column(name = "tag_name")
    private String tagName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
}
