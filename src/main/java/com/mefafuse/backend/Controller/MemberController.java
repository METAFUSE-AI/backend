package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    // 사용자, 프로필 불러오기
    // 테스트, 퀴즈 결과 불러오기

    @GetMapping("/test-result/{id}")
    public ResponseEntity<Member> LoadTestResult() { //해당 멤버의 테스트 id에 대한 결과 조회
        return null;
    }

    @GetMapping("/quiz-result/{id}")
    public ResponseEntity<Member> LoadQuizResult() { //해당 멤버의 테스트 id에 대한 결과 조회
        return null;
    }
}
