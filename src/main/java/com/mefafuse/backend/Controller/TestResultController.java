package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Entity.TestResult;
import com.mefafuse.backend.Repository.MemberIdRepository;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestRepository;
import com.mefafuse.backend.Repository.TestResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test-results")
public class TestResultController {

    private static final Logger logger = LoggerFactory.getLogger(TestResultController.class);

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private MemberIdRepository memberRepository;

    @Autowired
    private TestRepository testRepository;

    // 특정 멤버와 테스트 ID로 TestResult를 업데이트하는 엔드포인트
    @PutMapping("/update/{memberId}/{testId}")
    public ResponseEntity<TestResult> updateTestResult(@PathVariable Long memberId, @PathVariable Long testId, @RequestBody TestResult testResult) {
        // 로그로 데이터 확인
        logger.info("Received test result update request for memberId: {}, testId: {}", memberId, testId);

        // 멤버와 테스트를 조회
        Optional<Test> testOptional = testRepository.findById(testId);
        if (!testOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null); // 테스트가 존재하지 않으면 400 오류
        }

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (!memberOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null); // 멤버가 존재하지 않으면 400 오류
        }

        // 기존 테스트 결과 가져오기
        TestResult existingTestResult = testResultRepository.findTopByMember_MemberIdAndTest_TestIdOrderByTestRoundDesc(memberId, testId);

        if (existingTestResult == null) {
            return ResponseEntity.badRequest().body(null); // 기존 결과가 없으면 400 오류
        }

        // 기존 테스트 결과의 정보를 업데이트
        existingTestResult.setScore(testResult.getScore());
        existingTestResult.setMetaCognitionScore(testResult.getMetaCognitionScore());
        existingTestResult.setMonitoringScore(testResult.getMonitoringScore());
        existingTestResult.setMetaControlScore(testResult.getMetaControlScore());

        // 생성 날짜 설정 (현재 날짜)
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        existingTestResult.setCreatedAt(currentDate);

        // 데이터베이스에 저장 (업데이트)
        TestResult updatedTestResult = testResultRepository.save(existingTestResult);
        logger.info("Updated test result data: {}", updatedTestResult);

        return ResponseEntity.ok(updatedTestResult);
    }
}
