package com.sparta.spartaminiproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    DUPLICATE_USERNAME(BAD_REQUEST, "duplicate username"),
    UNREGISTER_USER(BAD_REQUEST, "unregister user"),
    INVALID_PASSWORD(BAD_REQUEST, "invalid password"),
    INVALID_DORMITORY(BAD_REQUEST, "invalid dormitory"),

    NULL_BOARD_DATA(BAD_REQUEST, "null board"),
    NULL_COMMENT_DATA(BAD_REQUEST, "null comment"),

    /* 401 UNAUTHORIZED : 인증 실패 */
    NOT_AUTHOR(UNAUTHORIZED, "not author"),
    INVALID_TOKEN(UNAUTHORIZED, "invalid token"),
    NULL_TOKEN(UNAUTHORIZED, "null token"),

    /* 403 FORBIDDEN : 인가 실패 */
    PERMISSION_DINED(FORBIDDEN, "forbidden");


    private final HttpStatus httpStatus;
    private final String message;

}
