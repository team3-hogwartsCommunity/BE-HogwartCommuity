package com.sparta.spartaminiproject.common.utill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SIGNUP_SUCCESS(HttpStatus.OK, "회원가입 성공"),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    CHECKUP_SUCCESS(HttpStatus.OK, "중복 없음"),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),


    BLOG_POST_SUCCESS(HttpStatus.OK,"게시글 작성 성공"),
    BLOG_PUT_SUCCESS(HttpStatus.OK,"게시글 수정 성공"),
    BLOG_DELETE_SUCCESS(HttpStatus.OK,"게시글 삭제 성공"),

    COMMENT_POST_SUCCESS(HttpStatus.OK,"댓글 작성 성공"),
    COMMENT_PUT_SUCCESS(HttpStatus.OK,"댓글 수정 성공"),
    COMMENT_DELETE_SUCCESS(HttpStatus.OK,"댓글 삭제 성공"),

    RECOMMENT_POST_SUCCESS(HttpStatus.OK, "대댓글 작성 성공"),
    RECOMMENT_PUT_SUCCESS(HttpStatus.OK, "대댓글 수정 성공"),
    RECOMMENT_DELETE_SUCCESS(HttpStatus.OK, "대댓글 삭제 성공"),

    LIKE_SUCCESS(HttpStatus.OK, "좋아요 선택"),
    NOT_LIKE_SUCCESS(HttpStatus.OK, "좋아요 취소");

    private final HttpStatus httpStatus;
    private final String msg;
}
