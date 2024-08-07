package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tests")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestRepository testRepository;

    @PostMapping
    public Test createTest(@RequestBody Test test) {
        // 로그로 데이터 확인
        logger.info("Received test data: {}", test);

        // 현재 시간 설정
        test.setTestDate(LocalDateTime.now());
        test.setCreatedAt(LocalDateTime.now().toString());
        test.setUpdatedAt(LocalDateTime.now().toString());

        // 데이터베이스에 저장
        Test savedTest = testRepository.save(test);
        logger.info("Saved test data: {}", savedTest);

        return savedTest;
    }
}
