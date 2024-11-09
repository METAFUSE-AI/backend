package com.mefafuse.backend.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 회원가입 실패 응답
    UsernameAlreadyExists(400, "M401", "이미 사용 중인 아이디입니다."),

    // 로그인 실패 응답
    UserNotFound(400, "L401", "존재하지 않는 아이디입니다."),
    InvalidPassword(400, "L402", "비밀번호가 올바르지 않습니다."),

    // 기록 관련 에러 응답
    RecordNotFound(404, "R404", "기록을 찾을 수 없습니다."),
    MemberNotFound(400, "R401", "존재하지 않는 멤버입니다."),

    // 테스트 관련 에러 응답
    TestCreationFailed(400, "T401", "테스트 생성에 실패했습니다."),
    InvalidQuestionScore(400, "T402", "유효하지 않은 질문 점수입니다."),

    // 테스트 결과 관련 에러 응답
    TestResultCreationFailed(400, "TR401", "테스트 결과 생성에 실패했습니다.");


    private final int status;
    private final String code;
    private final String message;
}
