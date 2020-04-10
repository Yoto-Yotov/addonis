package com.addonis.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@Where(clause = "enabled = 1")
public class User {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "enabled")
    private int enabled = 1;

    @Column(name = "password")
    private String password;

}
