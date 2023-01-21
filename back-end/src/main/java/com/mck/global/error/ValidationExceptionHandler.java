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
        "error": [
            {
                "code": "invalid_pattern",
                "field": "email",
                "message": "goldenplanet.co.kr 이메일만 사용 가능합니다"
            },
            {
                "code": "invalid_size",
                "field": "nickname",
                "message": "닉네임은 3자 이상 10자 이하로 입력해주세요."
            },
            {
                "code": "invalid_pattern",
                "field": "password",
                "message": "비밀번호는 최소 8자 이상 16자 이하, 하나 이상의 문자 및 숫자, 하나 이상의 특수문자가 포함되어야 합니다"
            }
        ]
    }
    */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("code", getCodeName(e.getCode()));
                    map.put("message", e.getDefaultMessage());
                    map.put("field", e.getField());
                    return map;
                })
                .collect(Collectors.toList());

        body.put("success", false);
        body.put("error", errors);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    private String getCodeName(String code){
        String code_name = "invalid_parameter";
        switch (code){
            case "Pattern":
                code_name = "invalid_pattern";
                break;
            case "Size":
                code_name = "invalid_size";
                break;
        }
        return code_name;
    }
}
