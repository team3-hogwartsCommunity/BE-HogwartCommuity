package com.sparta.spartaminiproject.domain.board.dto;

import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import lombok.Getter;

import java.util.List;

public class BoardResponseDto {

    @Getter
    public static class BoardListWithTotalCount {

        private final long totalPages;
        private final List<BoardList> boardLists;

        public BoardListWithTotalCount(long totalPages, List<BoardList> boardLists) {
            this.totalPages = totalPages;
            this.boardLists = boardLists;
        }
    }

    @Getter
    public static class BoardList {

        private final Long id;
        private final String title;
        private final String sub;
        private final Long like;
        private final String createAt;

        public BoardList(Board board, Long like) {
            this.id = board.getId();
            this.title = board.getTitle();
            sub = board.getContents();
            this.like = like;
            this.createAt = board.getCreatedAt().toString().replace("T", " T").substring(0, 20);
        }
    }

    @Getter
    public static class OneBoard {

        private final Long id;
        private final String username;
        private final String title;
        private final String contents;
        private final Long like;
        private final String createdAt;
        private final List<CommentDto.Response> commentList;

        public OneBoard(Board board, Long like, List<CommentDto.Response> commentList) {
            this.id = board.getId();
            this.username = board.getUser().getUsername();
            this.title = board.getTitle();
            this.contents = board.getContents();
            this.like = like;
            this.createdAt = board.getCreatedAt().toString().replace("T", " T").substring(0, 20);
            this.commentList = commentList;
        }
    }
}
