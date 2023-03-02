package com.sparta.spartaminiproject.domain.user.controller;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.service.UserService;
import com.sparta.spartaminiproject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SendMessageDto> signup(@RequestBody UserDto.SignupRequest signupRequest){
        return userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<SendMessageDto> login(@RequestBody UserDto.LoginRequest loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PostMapping("/checkup")
    public ResponseEntity<SendMessageDto> checkup(@RequestBody UserDto.CheckRequest checkRequest){
        return userService.checkUsername(checkRequest);
    }

    @PutMapping("/dormitory")
    public ResponseEntity<SendMessageDto> assigment(@RequestBody UserDto.AssigmentRequest assigmentRequest, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.checkdormitory(assigmentRequest, userDetails.getUser());
    }
}
