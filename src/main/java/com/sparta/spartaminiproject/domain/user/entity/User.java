package com.sparta.spartaminiproject.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private UserDormitory dormitory;

    @Builder
    public User(String username, String password, UserDormitory dormitory) {
        this.username = username;
        this.password = password;
        this.dormitory = dormitory;
    }
}
