package com.addonis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Authorities
 * Model used for managing user role.
 * User can be either admin, registered user or just user (no permissions and no Authority)
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
