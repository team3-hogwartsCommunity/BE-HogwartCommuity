package com.sparta.spartaminiproject.domain.user.controller;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/logout")
    @ResponseBody
    public ResponseEntity<SendMessageDto> logoutPage(HttpServletRequest request, HttpServletResponse response){

        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.LOGIN_SUCCESS));// 내부 로직 성공, html파일 넣어주면 될것 해보기
    }
}
