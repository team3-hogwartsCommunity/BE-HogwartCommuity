package com.sparta.spartaminiproject.domain.user.service;

import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import com.sparta.spartaminiproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

import static com.sparta.spartaminiproject.domain.user.entity.UserDormitory.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(UserDto.SignupRequest signupRequest){
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
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
    public void login(UserDto.LoginRequest loginRequest, HttpServletResponse response){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        //아이디 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 등록되지 않았습니다.")
        );

        if(!password.equals(user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
