package com.addonis.demo.models;

import lombok.Data;

import javax.persistence.*;

@Table(name = "authorities")
//@Entity
@Data
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

    public Authorities() {
    }
}
