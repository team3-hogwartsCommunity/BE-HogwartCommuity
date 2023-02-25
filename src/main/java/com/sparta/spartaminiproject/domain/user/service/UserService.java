package com.sparta.spartaminiproject.domain.user.service;

import com.sparta.spartaminiproject.domain.user.dto.UserDto;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import com.sparta.spartaminiproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
        User user = User.builder()
                .username(username)
                .password(password)
                .dormitory(dormitory)
                .build();
        userRepository.save(user);
    }

}
