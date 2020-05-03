package com.addonis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Readme is class that represents the addon github readme.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "readme")
public class Readme {

    @Id
    @Column(name = "readme_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int readmeId;

    @Column(name = "text")
    private byte[] text;
}
