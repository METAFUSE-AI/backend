package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 테스트 생성
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Map<String, Long>> createTest(@RequestBody Test test) {
        String username = test.getMember().getUsername();  // username을 가져옴
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Member not found with username " + username));

        // Test 객체 설정
        test.setMember(member);
        test.setCreatedAt(new Date());
        test.setUpdatedAt(new Date());

        // username을 기준으로 최대 seq 값을 가져옴
        Integer maxSeq = testRepository.findMaxSeqByUsername(username);
        test.setSeq((maxSeq != null ? maxSeq : 0) + 1);

        // 점수 세팅
        test.setTotalScore(test.getTotalScore());
        test.setMetaCognitionScore(test.getMetaCognitionScore());
        test.setMonitoringScore(test.getMonitoringScore());
        test.setMetaControlScore(test.getMetaControlScore());

        // 테스트 저장
        Test savedTest = testRepository.save(test);

        // testId를 반환
        Map<String, Long> response = new HashMap<>();
        response.put("testId", savedTest.getTestId());

        return ResponseEntity.ok(response);
    }

    // 특정 testId에 대한 점수 요약 정보를 반환
    @GetMapping("/{testId}/scores")
    public ResponseEntity<Map<String, Object>> getScoreSummaryByTest(@PathVariable Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found with testId " + testId));

        Member member = test.getMember();  // Test에서 Member 정보 가져오기

        // 점수 초기화
        int totalScoreSum = test.getTotalScore();
        int metaCognitionScoreSum = test.getMetaCognitionScore();
        int monitoringScoreSum = test.getMonitoringScore();
        int metaControlScoreSum = test.getMetaControlScore();

        // 응답 객체 생성
        Map<String, Object> scoreSummary = new HashMap<>();
        scoreSummary.put("totalScoreSum", totalScoreSum);
        scoreSummary.put("metaCognitionScoreSum", metaCognitionScoreSum);
        scoreSummary.put("monitoringScoreSum", monitoringScoreSum);
        scoreSummary.put("metaControlScoreSum", metaControlScoreSum);

        Map<String, Object> scoreDetail = new HashMap<>();
        scoreDetail.put("testId", test.getTestId());
        scoreDetail.put("date", test.getCreatedAt());
        scoreDetail.put("totalScore", test.getTotalScore());
        scoreDetail.put("metaCognitionScore", test.getMetaCognitionScore());
        scoreDetail.put("monitoringScore", test.getMonitoringScore());
        scoreDetail.put("metaControlScore", test.getMetaControlScore());

        scoreSummary.put("scoreDetails", List.of(scoreDetail)); // 세부 정보

        return ResponseEntity.ok(scoreSummary);
    }

    // username을 기준으로 테스트 결과를 가져오는 메서드
    @GetMapping("/{username}")
    public ResponseEntity<List<Map<String, Object>>> getTestResultsByUsername(@PathVariable String username) {
        // username에 해당하는 테스트 목록 가져오기
        List<Test> tests = testRepository.findByMember_Username(username);  // username으로 테스트를 찾음

        // 결과 리스트 생성
        List<Map<String, Object>> testResults = tests.stream().map(test -> {
            Map<String, Object> result = new HashMap<>();
            result.put("testId", test.getTestId());
            result.put("seq", test.getSeq());
            result.put("createdAt", test.getCreatedAt());
            return result;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(testResults);  // 테스트 결과 반환
    }

}
