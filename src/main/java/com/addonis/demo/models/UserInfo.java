package com.addonis.demo.models;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usersinfo")
@Where(clause = "enabled = 1")
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "enabled")
    private Integer enabled = 1;

    @NotBlank
    @NotNull
    @OneToOne
    @JoinColumn(name = "username")
    private User name;

    @NotBlank
    @NotNull
    @Column(name = "user_email")
    private String email;

    @Lob
    @Column(name = "user_picture")
    private Byte[] profileImage;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public UserInfo() {

    }
}
