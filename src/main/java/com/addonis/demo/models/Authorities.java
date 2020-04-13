package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Data
@Table(name = "authorities")
public class Authorities {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

}
