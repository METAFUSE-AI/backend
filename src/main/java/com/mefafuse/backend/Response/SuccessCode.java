package com.mefafuse.backend.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // 회원가입 성공 응답
    
    // 로그인 성공 응답
    // 로그인 시 사용자가 존재하지 않을 경우
    LoginSuccess(200, "L001", "로그인 성공");

    private final int status;
    private final String code;
    private final String message;
}
