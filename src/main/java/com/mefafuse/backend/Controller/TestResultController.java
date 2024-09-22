package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.TestResult;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-results")
public class TestResultController {

    private static final Logger logger = LoggerFactory.getLogger(TestResultController.class);

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 새로운 테스트 결과를 생성하고 회차 정보를 설정하는 엔드포인트
    @PostMapping
    public TestResult createTestResult(@RequestBody TestResult testResult) {
        // 로그로 데이터 확인
        logger.info("Received test result data: {}", testResult);

        // 해당 멤버의 기존 테스트 결과 가져오기
        Long memberId = testResult.getMember().getMemberId();
        List<TestResult> memberResults = testResultRepository.findByMember_MemberId(memberId);

        // 회차 정보 설정
        int testRound = memberResults.size() + 1;
        testResult.setTestRound(testRound); // 회차 정보 설정

        // 데이터베이스에 저장
        TestResult savedTestResult = testResultRepository.save(testResult);
        logger.info("Saved test result data: {}", savedTestResult);

        return savedTestResult;
    }

    // 모든 테스트 결과를 반환하는 엔드포인트
    @GetMapping("/all")
    public List<TestResult> getAllTestResults() {
        return testResultRepository.findAll();
    }

    // 특정 멤버의 테스트 회차별 결과를 가져오는 엔드포인트
    @GetMapping("/member/{memberId}")
    public List<TestResult> getTestResultsByMember(@PathVariable("memberId") Long memberId) {
        return testResultRepository.findByMember_MemberIdOrderByTestRoundAsc(memberId);
    }
}
