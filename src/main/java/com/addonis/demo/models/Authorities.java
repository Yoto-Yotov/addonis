package com.addonis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Authorities is a class representing the user roles and rights.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;
}
