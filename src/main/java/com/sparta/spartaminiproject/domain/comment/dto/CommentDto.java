package com.sparta.spartaminiproject.domain.comment.dto;

import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.recomment.dto.ReCommentDto;
import lombok.Getter;

import java.util.List;

public class CommentDto {

    @Getter
    public static class Request {
        private String contents;
    }

    @Getter
    public static class Response {

        private final Long id;
        private final String username;
        private final String contents;
        private final Long like;
        private final String createdAt;
        private final List<ReCommentDto.Response> reCommentList;

        public Response(Comment comment, Long like, List<ReCommentDto.Response> reCommentList) {
            this.id = comment.getId();
            this.username = comment.getUser().getUsername();
            this.contents = comment.getContents();
            this.like = like;
            this.createdAt = comment.getCreatedAt().toString().replace("T", " T").substring(0, 20);
            this.reCommentList = reCommentList;
        }
    }
}
