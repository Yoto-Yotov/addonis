package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * IDE - class representing the IDE of each addon.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "ide")
public class IDE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ide_id")
    private int ideId;

    @Column(name = "ide_name")
    private String ideName;

}
