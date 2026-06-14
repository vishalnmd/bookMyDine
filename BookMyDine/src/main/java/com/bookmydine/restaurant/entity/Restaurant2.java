package com.bookmydine.restaurant.entity;

import com.bookmydine.common.entity.BaseEntity;
import com.bookmydine.common.enums.FoodType;
import com.bookmydine.user.entity.User;
import jakarta.persistence.*;

@Entity
public class Restaurant2 extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @ManyToOne
    @JoinColumn(name = "user_id")  // ← FK in ORDER table
    private User user;

}
