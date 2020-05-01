package com.addonis.demo.models;

import lombok.Data;

import javax.persistence.*;

/**
 * BinaryContent class is used for the content of the addon. (update and download)
 */
@Entity
@Data
@Table(name = "binary_content")
public class BinaryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String docName;

    @Column
    private String type;

    @Column
    @Lob
    private byte[] file;
}
