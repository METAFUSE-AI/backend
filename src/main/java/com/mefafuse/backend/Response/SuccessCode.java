package com.mefafuse.backend.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // 회원가입 성공 응답
    RegisterSuccess(201, "M001", "회원가입 성공"),
    CheckUsernameSuccess(200, "M002", "아이디 중복 확인 성공"),
    
    // 로그인 성공 응답
    LoginSuccess(200, "L001", "로그인 성공"),
    LoginNoUserInfo(200, "L002", "로그인 실패 : 사용자 정보가 존재하지 않습니다"),

    // 기록 관련 성공 응답
    RecordCreationSuccess(201, "R001", "기록 생성 성공"),
    RecordFetchSuccess(200, "R002", "기록 조회 성공"),
    RecordUpdateSuccess(200, "R003", "기록 수정 성공"),
    RecordDeletionSuccess(204, "R004", "기록 삭제 성공"),

    // 테스트 관련 성공 응답
    TestCreationSuccess(201, "T001", "테스트 생성 성공"),

    // 테스트 결과 관련 성공 응답
    TestResultCreationSuccess(201, "TR001", "테스트 결과 생성 성공");

    private final int status;
    private final String code;
    private final String message;
}
