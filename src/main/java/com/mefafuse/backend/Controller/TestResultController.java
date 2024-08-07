package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.TestResult;
import com.mefafuse.backend.Repository.TestResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-results")
public class TestResultController {

    private static final Logger logger = LoggerFactory.getLogger(TestResultController.class);

    @Autowired
    private TestResultRepository testResultRepository;

    @PostMapping
    public TestResult createTestResult(@RequestBody TestResult testResult) {
        // 로그로 데이터 확인
        logger.info("Received test result data: {}", testResult);

        // 데이터베이스에 저장
        TestResult savedTestResult = testResultRepository.save(testResult);
        logger.info("Saved test result data: {}", savedTestResult);

        return savedTestResult;
    }
}
