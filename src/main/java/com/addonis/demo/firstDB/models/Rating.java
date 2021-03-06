package com.addonis.demo.firstDB.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Rating is a class that represents addons rating. Addon can be rated and visualize the average rating.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "addon_id")
    private Addon addon;

    @Column(name = "rating")
    private double rating;

}
