package com.mefafuse.backend.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 회원가입 실패 응답
    
    // 로그인 실패 응답
    LoginError(500, "L501", "로그인 실패");

    private final int status;
    private final String code;
    private final String message;
}
