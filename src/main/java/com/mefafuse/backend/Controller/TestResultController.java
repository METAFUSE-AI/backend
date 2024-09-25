package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.TestResult;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TestResult> createTestResult(@RequestBody TestResult testResult) {
        // 로그로 데이터 확인
        logger.info("Received test result data: {}", testResult);

        // 해당 멤버의 기존 테스트 결과 가져오기
        Long memberId = testResult.getMember().getMemberId();
        List<TestResult> memberResults = testResultRepository.findByMember_MemberId(memberId);

        // 회차 정보 설정
        int testRound = memberResults.size() + 1;
        testResult.setTestRound(testRound); // 회차 정보 설정

        // 테스트에서 점수 계산
        calculateTestScores(testResult);

        // 데이터베이스에 저장
        TestResult savedTestResult = testResultRepository.save(testResult);
        logger.info("Saved test result data: {}", savedTestResult);

        return ResponseEntity.ok(savedTestResult);
    }

    // 점수를 세 가지 카테고리로 나누는 메서드
    private void calculateTestScores(TestResult testResult) {
        // 테스트 결과에서 점수 분배를 위해 가정된 질문 점수 리스트
        int[] questionScores = testResult.getTest().getQuestionScores();

        if (questionScores == null || questionScores.length != 24) {
            logger.error("Invalid question scores. Expected 24 scores but got: {}",
                    questionScores == null ? "null" : questionScores.length);
            return;
        }

        // 각 카테고리의 점수 초기화
        int metaCognitionScore = 0;
        int monitoringScore = 0;
        int metaControlScore = 0;

        // 8개씩 3개의 카테고리로 점수를 나누어 합산
        for (int i = 0; i < questionScores.length; i++) {
            if (i < 8) {
                metaCognitionScore += questionScores[i]; // 첫 8개의 질문은 메타인지 점수
            } else if (i < 16) {
                monitoringScore += questionScores[i]; // 그다음 8개의 질문은 모니터링 점수
            } else {
                metaControlScore += questionScores[i]; // 마지막 8개의 질문은 메타통제 점수
            }
        }

        // 각 카테고리의 점수를 TestResult 객체에 설정
        testResult.setMetaCognitionScore(metaCognitionScore);
        testResult.setMonitoringScore(monitoringScore);
        testResult.setMetaControlScore(metaControlScore);

        // 총합 점수 설정
        int totalScore = metaCognitionScore + monitoringScore + metaControlScore;
        testResult.setScore(totalScore);

        // 로그로 각 점수 확인
        logger.info("Calculated Scores - MetaCognition: {}, Monitoring: {}, MetaControl: {}, Total: {}",
                metaCognitionScore, monitoringScore, metaControlScore, totalScore);
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
