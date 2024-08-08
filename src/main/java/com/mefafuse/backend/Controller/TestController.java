package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/tests")
@CrossOrigin(origins = "http://localhost:8080") // 클라이언트 URL
public class TestController {
    // API 메소드

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/create")
    public Test createTest(@RequestBody Test test) {
        // Member ID를 여기서 설정
        Long memberId = 1L; // 예시: 항상 1번 멤버로 설정 (실제 구현에서는 적절한 로직으로 변경 필요)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        // Test 엔티티에 Member 설정
        test.setMember(member);
        test.setCreatedAt(new Date());
        test.setUpdatedAt(new Date());

        return testRepository.save(test);
    }
}
