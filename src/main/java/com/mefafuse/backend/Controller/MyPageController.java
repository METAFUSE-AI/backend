package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


// 마이페이지 - 테스트 결과  점수별 삼각 그래프, 테스트 결과 가져오기, sns 공유 시스템
@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MemberRepository memberRepository;

}
