package com.mck.global.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    /* response 예제
    {
        "success": false,
            "error": {
        "messages": [
            "닉네임은 영문, 숫자, 한글만 가능합니다.",
            "goldenplanet.co.kr 이메일만 사용 가능합니다"
        ],
        "statusCode": "BAD_REQUEST",
        "timestamp": "2023-01-20T12:22:01.226581"
        }
    }
    */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> error = new HashMap<>();

        error.put("code", HttpStatus.BAD_REQUEST);

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        error.put("messages", errors);

        body.put("success", false);
        body.put("error", error);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
