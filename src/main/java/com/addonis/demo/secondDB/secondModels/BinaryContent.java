package com.addonis.demo.secondDB.secondModels;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;

/**
 * BinaryContent class is used for the content of the addon. (update and download)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "binary_content", schema = "binary_addonis_db")
@JsonSerialize
public class BinaryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String docName;

    @Column
    private String type;

    @Column
    @Lob
    private byte[] file;
}
