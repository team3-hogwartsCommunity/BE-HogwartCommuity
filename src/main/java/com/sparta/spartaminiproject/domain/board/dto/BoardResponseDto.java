package com.sparta.spartaminiproject.domain.board.dto;

import com.sparta.spartaminiproject.domain.board.entity.Board;
import lombok.Getter;

public class BoardResponseDto {

    @Getter
    public static class BoardList {

        private final Long id;
        private final String title;
        private final String sub;
        private final Long like;
        private final String createAt;

        public BoardList(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            sub = board.getContents();
            this.like = 0L;
            this.createAt = board.getCreatedAt().toString().replace("T", " T").substring(0, 20);
        }
    }

    @Getter
    public static class OneBoard {

        private final Long id;
        private final String title;
        private final String contents;
        private final Long like;
        private final String createAt;

        public OneBoard(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.contents = board.getContents();
            this.like = 0L;
            this.createAt = board.getCreatedAt().toString().replace("T", " T").substring(0, 20);
        }
    }
}
