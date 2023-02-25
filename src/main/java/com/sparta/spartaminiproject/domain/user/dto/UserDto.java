package com.sparta.spartaminiproject.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    public static class SignupRequest{
        private String username;
        private String password;
    }
}
