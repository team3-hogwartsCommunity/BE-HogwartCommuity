package com.sparta.spartaminiproject.domain.user.dto;

import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import lombok.Getter;
public class UserDto {
    @Getter
    public static class SignupRequest{
        private String username;
        private String password;
        private UserDormitory dormitory;
    }
    @Getter
    public static class LoginRequest{
        private String username;
        private String password;
    }
}
