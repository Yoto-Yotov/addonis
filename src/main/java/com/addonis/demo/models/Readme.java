package com.addonis.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "readme")
public class Readme {

    @Id
    @Column(name = "readme_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int readmeId;

    @Column(name = "text")
    private byte[] text;
}
