package com.sparta.spartaminiproject.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SendMessageDto {
    private String message;
    private int statusCode;

    @Builder
    public SendMessageDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
