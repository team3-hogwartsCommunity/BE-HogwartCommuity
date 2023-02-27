package com.sparta.spartaminiproject.domain.comment.dto;

import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import lombok.Getter;

public class CommentDto {

    @Getter
    public static class Request {
        private String contents;
    }

    @Getter
    public static class Response {

        private final Long id;
        private final String contents;
        private final Long like;
        private final String createAt;

        public Response(Comment comment) {
            this.id = comment.getId();
            this.contents = comment.getContents();
            this.like = 0L;
            this.createAt = comment.getCreatedAt().toString().replace("T", " T").substring(0, 20);
        }
    }
}
