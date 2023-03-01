package com.sparta.spartaminiproject.domain.recomment.dto;

import com.sparta.spartaminiproject.domain.recomment.entity.ReComment;
import lombok.Getter;

public class ReCommentDto {

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

        public Response(ReComment reComment, Long like) {
            this.id = reComment.getId();
            this.contents = reComment.getContents();
            this.like = like;
            this.createAt = reComment.getCreatedAt().toString().replace("T", " T").substring(0, 20);
        }
    }
}
