package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;
import com.mefafuse.backend.Repository.TestRepository;
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
    private MemberRepository memberRepository;

    @PostMapping("/create")
    public Test createTest(@RequestBody Test test) {
        // 여기서 멤버 ID를 받아서 처리
        Long memberId = test.getMember().getMemberId(); // 프론트에서 받은 memberId 사용
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        // Test 엔티티에 Member 설정
        test.setMember(member);
        test.setCreatedAt(new Date());
        test.setUpdatedAt(new Date());

        return testRepository.save(test);
    }
}
