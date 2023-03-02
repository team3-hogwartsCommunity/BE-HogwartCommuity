package com.sparta.spartaminiproject.domain.user.entity;

import com.sparta.spartaminiproject.common.utill.UserDormitory;
import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.user.dto.UserDto;
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

    @Column(nullable = false)
    private UserDormitory dormitory;

    @Builder
    public User(String username, String password, UserDormitory dormitory) {
        this.username = username;
        this.password = password;
        this.dormitory = dormitory;
    }
    public void update(UserDto.AssigmentRequest assigmentRequest) {
        this.dormitory=getDormitory();
    }
}
