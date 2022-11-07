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
    ALREADY_EXIST_NICKNAME(400, "이미 존재하는 닉네임입니다."),

    // 저장
    NOT_SAVE_POST(400, "글이 저장되지 않았습니다."),

    // 수정
    MISMATCHED_ENTER_PASSWORD(401, "입력한 비밀번호가 기존 비밀번호와 일치하지 않습니다."),
    MISMATCHED_ENTER_NICKNAME(401, "입력한 닉네임이 기존 닉네임이 일치하지 않습니다.");

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;
}
