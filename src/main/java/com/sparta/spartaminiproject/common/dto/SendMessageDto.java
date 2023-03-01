package com.sparta.spartaminiproject.common.dto;

import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class SendMessageDto {
    private String message;
    private int statusCode;

    @Builder
    public SendMessageDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    //    public static SendMessageDto of(HttpStatus status, String msg)
//    {
//        return SendMessageDto.builder()
//                .statusCode(status.value())
//                .message(msg)
//                .build();
//    }
    public static SendMessageDto of(SuccessCode successCode) {
        return SendMessageDto.builder()
                .statusCode(successCode.getHttpStatus().value())
                .message(successCode.getMsg())
                .build();
    }

    public static ResponseEntity<SendMessageDto> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus().value())
                .body(SendMessageDto.builder()
                .statusCode(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build());
    }
}
