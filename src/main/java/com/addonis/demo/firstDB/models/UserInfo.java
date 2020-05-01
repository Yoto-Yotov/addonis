package com.addonis.demo.firstDB.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * UserInfo is a class containing the whole user information and is used also for visualization.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usersinfo")
@Where(clause = "enabled = 1")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "enabled")
    private Integer enabled = 1;

    @NotNull
    @NotBlank
    @Column(name = "username")
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "user_email")
    private String email;

    @Lob
    @Column(name = "user_picture")
    private Byte[] profileImage;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

}
