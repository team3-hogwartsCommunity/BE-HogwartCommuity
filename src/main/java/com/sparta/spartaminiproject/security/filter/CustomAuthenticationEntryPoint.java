package com.sparta.spartaminiproject.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.exception.CustomException;
import com.sparta.spartaminiproject.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        log.error("인증 실패");

        CustomException exception = new CustomException(ErrorCode.INVALID_TOKEN);

        response.setStatus(exception.getErrorCode().getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), SendMessageDto.of(ErrorCode.INVALID_TOKEN));
    }
}
