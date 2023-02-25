package com.sparta.spartaminiproject.domain.user.dto;

import lombok.Getter;
public class UserDto {
    @Getter
    public static class SignupRequest{
        private String username;
        private String password;
        private String dormitory;
    }
    @Getter
    public static class LoginRequest{
        private String username;
        private String password;
    }
}
