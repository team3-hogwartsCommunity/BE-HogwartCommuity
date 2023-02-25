package com.sparta.spartaminiproject.domain.user.controller;

import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Validated
    @PostMapping("/signup")
    public String signup(@RequestBody UserDto.SignupRequest signupRequest){
        userService.signup(signupRequest);
        return "회원가입 성공";
    }
}
