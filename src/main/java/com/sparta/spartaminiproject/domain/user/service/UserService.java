package com.sparta.spartaminiproject.domain.user.service;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.common.security.jwt.JwtUtil;
import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import com.sparta.spartaminiproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserDto.SignupRequest signupRequest){
        String username = signupRequest.getUsername();
        String password = passwordEncoder.encode(signupRequest.getPassword());
        UserDormitory dormitory = UserDormitory.NONE;


        //중복확인
        Optional<User> check = userRepository.findByUsername(username);
        if (check.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //기숙사 확인
//        UserDormitory dormitory = UserDormitory.NONE;
//        if (signupRequest.getDormitory().equals(Gryffindor)) {
//            dormitory = UserDormitory.Gryffindor;
//        } else if (signupRequest.getDormitory().equals(Hufflepuff)) {
//            dormitory = Hufflepuff;
//        }else if (signupRequest.getDormitory().equals(Ravenclaw)) {
//            dormitory = Ravenclaw;
//        }else dormitory = Slytherin;


        User user = User.builder()
                .username(username)
                .password(password)
                .dormitory(dormitory)
                .build();
        userRepository.save(user);
    }
    @Transactional
    public ResponseEntity<SendMessageDto> login(UserDto.LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        UserDormitory dormitory = UserDormitory.NONE;


        //아이디 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("사용자가 등록되지 않았습니다.")
//        );

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new IllegalArgumentException("사용자가 등록되지 않았습니다");
        } else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

//        if(!password.equals(user.getPassword())){
//            return ResponseEntity.badRequest()
//                    .body(SendMessageDto.builder()
//                            .statusCode(HttpStatus.BAD_REQUEST.value())
//                            .message("비밀번호가 일치하지 않습니다.")
//                            .build());
//        }
        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getDormitory()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(SendMessageDto.of(SuccessCode.LOGIN_SUCCESS));
    }
}
