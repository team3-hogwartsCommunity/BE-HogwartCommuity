package com.sparta.spartaminiproject.domain.user.service;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.exception.CustomException;
import com.sparta.spartaminiproject.exception.ErrorCode;
import com.sparta.spartaminiproject.security.jwt.JwtUtil;
import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.common.utill.UserDormitory;
import com.sparta.spartaminiproject.domain.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.sparta.spartaminiproject.common.utill.UserDormitory.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<SendMessageDto> signup(UserDto.SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = passwordEncoder.encode(signupRequest.getPassword());
        UserDormitory dormitory;

        //중복확인
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        switch(signupRequest.getDormitory()) {
            case Gryffindor: dormitory = UserDormitory.Gryffindor;
                break;
            case Hufflepuff: dormitory = UserDormitory.Hufflepuff;
                break;
            case Ravenclaw: dormitory = UserDormitory.Ravenclaw;
                break;
            case Slytherin: dormitory = UserDormitory.Slytherin;
                break;
            default: throw new CustomException(ErrorCode.INVALID_DORMITORY);
        }

//        if (signupRequest.getDormitory() == Gryffindor) {
//            dormitory = UserDormitory.Gryffindor;
//        } else if (signupRequest.getDormitory() == Hufflepuff) {
//            dormitory = UserDormitory.Hufflepuff;
//        } else if (signupRequest.getDormitory() == Ravenclaw) {
//            dormitory = UserDormitory.Ravenclaw;
//        } else if (signupRequest.getDormitory() == Slytherin) {
//            dormitory = UserDormitory.Slytherin;
//        } else {
//            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
//        }

        User user = User.builder()
                .username(username)
                .password(password)
                .dormitory(dormitory)
                .build();
        userRepository.save(user);

        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.SIGNUP_SUCCESS));
    }

    @Transactional
    public ResponseEntity<SendMessageDto> login(UserDto.LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        UserDormitory dormitory = UserDormitory.NONE;

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new CustomException(ErrorCode.UNREGISTER_USER);
        } else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getDormitory()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(SendMessageDto.of(SuccessCode.LOGIN_SUCCESS));
    }

    public ResponseEntity<SendMessageDto> checkUsername(UserDto.CheckRequest checkRequest) {
        String username = checkRequest.getUsername();
        //중복확인
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }
        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.CHECKUP_SUCCESS));
    }

    public ResponseEntity<SendMessageDto> checkdormitory(UserDto.AssigmentRequest assigmentRequest, User user) {
        //기숙사 확인
        UserDormitory dormitory = UserDormitory.NONE;


        User users = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.DUPLICATE_USERNAME)
        );

        users.update(assigmentRequest);
        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.CHECKUP_SUCCESS));
    }
}
