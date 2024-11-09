package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Entity.TestResult;
import com.mefafuse.backend.Repository.MemberIdRepository;
import com.mefafuse.backend.Repository.TestRepository;
import com.mefafuse.backend.Repository.TestResultRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = "http://172.30.1.36:19006")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MemberIdRepository memberRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @PostMapping("/create")
    @Transactional
    public Test createTest(@RequestBody Test test) {
        Long memberId = test.getMember().getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        // Test 엔티티에 Member 설정
        test.setMember(member);
        test.setCreatedAt(new Date());
        test.setUpdatedAt(new Date());

        // Test 엔티티 저장
        Test savedTest = testRepository.save(test);

        return savedTest;
    }

    // TestResult 저장 시 round 값을 자동으로 설정하는 엔드포인트
    @PostMapping("/result")
    public TestResult saveTestResult(@RequestBody TestResult testResult) {
        Long memberId = testResult.getMember().getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        // TestResult에 연결된 Test가 있는지 확인하고 저장
        Test test = testResult.getTest();
        if (test == null || !testRepository.existsById(test.getTestId())) {
            throw new RuntimeException("Test not found");
        }

        // 해당 회원에 대한 최신 TestResult를 조회하여 round 값 설정
        TestResult latestTestResult = testResultRepository.findTopByMember_MemberIdAndTest_TestIdOrderByTestRoundDesc(memberId, test.getTestId());

        // round 값 설정 (최대 round 값 + 1 또는 처음이면 1)
        int round = (latestTestResult == null) ? 1 : latestTestResult.getTestRound() + 1;
        testResult.setTestRound(round);

        // TestResult 설정
        testResult.setCreatedAt(new Date().toString());

        // TestResult 저장
        return testResultRepository.save(testResult);
    }
}
