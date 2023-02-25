package com.sparta.spartaminiproject.domain.board.dto;

import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import lombok.Getter;

public class BoardRequestDto {

    @Getter
    public static class Write {
        private String title;
        private String contents;
        private UserDormitory dormitory;
    }

    @Getter
    public static class Edit {
        private String title;
        private String contents;
    }
}
