package com.addonis.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "authorities")
public class Authorities {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

}
