package com.mefafuse.backend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Entity.TestResult;
import com.mefafuse.backend.Repository.MemberIdRepository;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestRepository;
import com.mefafuse.backend.Repository.TestResultRepository;
import com.mefafuse.backend.Response.ApiResponse;
import com.mefafuse.backend.Response.ErrorCode;
import com.mefafuse.backend.Response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = "http://172.30.1.36:19006")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MemberIdRepository memberRepository;

    @Autowired
    private TestResultRepository testResultRepository;  // TestResultRepository 추가

    private final ObjectMapper objectMapper = new ObjectMapper();  // JSON 파싱을 위한 ObjectMapper

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createTest(@RequestBody Test test) {
        Optional<Member> memberOptional = memberRepository.findById(test.getMember().getMemberId());
        if (!memberOptional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.errorResponse(ErrorCode.MemberNotFound));
        }

        test.setMember(memberOptional.get());
        test.setCreatedAt(new Date());
        test.setUpdatedAt(new Date());

        int score = calculateScoreFromQuestion(test.getQuestion());
        if (score == -1) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.errorResponse(ErrorCode.InvalidQuestionScore));
        }

        test.setTestScore(score);
        Test createdTest = testRepository.save(test);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.successResponse(SuccessCode.TestCreationSuccess, createdTest));
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
