package com.mck.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 인증
    ALREADY_REGISTERED_MEMBER(400, "이미 가입된 회원 입니다."),
    MISMATCHED_PASSWORD(401, "패스워드가 일치하지 않습니다."),
    MISMATCHED_FORMAT(401, "형식이 맞지 않습니다."),
    LOGIN_ERROR(401, "아이디 또는 비밀번호를 확인해주세요"),
    NOT_EXISTING_ACCOUNT(400, "존재하지 않는 회원 입니다."),

    // 저장
    NOT_SAVE_POST(400, "글이 저장되지 않았습니다.");

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;
}
