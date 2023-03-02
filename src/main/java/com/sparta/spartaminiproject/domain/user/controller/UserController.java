package com.sparta.spartaminiproject.domain.user.controller;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.service.UserService;
import com.sparta.spartaminiproject.security.jwt.JwtUtil;
import com.sparta.spartaminiproject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<SendMessageDto> signup(@RequestBody UserDto.SignupRequest signupRequest){
        return userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<SendMessageDto> login(@RequestBody UserDto.LoginRequest loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<SendMessageDto> logout(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.expireToken(token));
        return ResponseEntity.ok()
                .headers(headers)
                .body(SendMessageDto.of(SuccessCode.LOGOUT_SUCCESS));
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
