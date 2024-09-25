package com.mefafuse.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Entity.TestResult;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestRepository;
import com.mefafuse.backend.Repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = "http://172.30.1.36:19006")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestResultRepository testResultRepository;  // TestResultRepository 추가

    private final ObjectMapper objectMapper = new ObjectMapper();  // JSON 파싱을 위한 ObjectMapper

    @PostMapping("/create")
    public Test createTest(@RequestBody Test test) {
        // 멤버 ID를 받아서 처리
        Long memberId = test.getMember().getMemberId(); // 프론트에서 받은 memberId 사용
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        // Test 엔티티에 Member 설정
        test.setMember(member);
        test.setCreatedAt(new Date());
        test.setUpdatedAt(new Date());

        // question의 값들이 key-value 형식일 경우 value들을 더해서 score에 저장
        String question = test.getQuestion();
        int score = calculateScoreFromQuestion(question);  // 점수 계산
        test.setTestScore(score);  // 계산된 점수를 Test 엔티티의 testScore에 설정

        // Test 엔티티 저장
        Test savedTest = testRepository.save(test);

        // TestResult 엔티티 생성 및 저장 (TestResult 테이블에 데이터 삽입)
        TestResult testResult = new TestResult();
        testResult.setTest(savedTest);
        testResult.setMember(member);  // Test와 연결된 Member 설정
        testResult.setScore(score);    // Test에서 계산된 점수 저장
        testResult.setResultType("Final Result");  // 임의의 resultType 설정
        testResult.setCreatedAt(new Date().toString());

        // TestResult 저장
        testResultRepository.save(testResult);

        return savedTest;
    }

    // question (key-value) 값으로부터 score를 계산하는 메서드
    private int calculateScoreFromQuestion(String question) {
        int totalScore = 0;
        try {
            // question을 JSON으로 파싱
            JsonNode rootNode = objectMapper.readTree(question);

            // key-value 값을 하나씩 순회하며 value 값을 더함
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode valueNode = field.getValue();

                // value 값이 숫자인 경우에만 더함
                if (valueNode.isInt()) {
                    totalScore += valueNode.asInt();
                } else {
                    System.out.println("Invalid value for key: " + field.getKey());
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return totalScore;
    }
}
