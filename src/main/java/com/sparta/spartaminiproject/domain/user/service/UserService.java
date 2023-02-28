package com.sparta.spartaminiproject.domain.user.service;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.common.security.jwt.JwtUtil;
import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import com.sparta.spartaminiproject.domain.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.spartaminiproject.domain.user.entity.UserDormitory.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserDto.SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = passwordEncoder.encode(signupRequest.getPassword());
//        UserDormitory dormitory = UserDormitory.NONE;


        //중복확인
        Optional<User> check = userRepository.findByUsername(username);
        if (check.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //기숙사 확인
        UserDormitory dormitory = UserDormitory.NONE;
        if (signupRequest.getDormitory() == Gryffindor) {
            dormitory = UserDormitory.Gryffindor;
        } else if (signupRequest.getDormitory() == Hufflepuff) {
            dormitory = UserDormitory.Hufflepuff;
        } else if (signupRequest.getDormitory() == Ravenclaw) {
            dormitory = UserDormitory.Ravenclaw;
        } else if (signupRequest.getDormitory() == Slytherin) {
            dormitory = UserDormitory.Slytherin;
        }


            User user = User.builder()
                    .username(username)
                    .password(password)
                    .dormitory(dormitory)
                    .build();
        userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<SendMessageDto> login(UserDto.LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        UserDormitory dormitory = UserDormitory.NONE;

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자가 등록되지 않았습니다");
        } else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getDormitory()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(SendMessageDto.of(SuccessCode.LOGIN_SUCCESS));
    }
}
