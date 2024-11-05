// MemberController 클래스
package com.mefafuse.backend.Controller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Member member) {
        Map<String, String> response = new HashMap<>();

        if (memberRepository.existsByUsername(member.getUsername())) {
            response.put("message", "이미 사용 중인 아이디입니다.");
            return ResponseEntity.badRequest().body(response);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 해시화
        memberRepository.save(member);
        response.put("message", "회원가입 성공");
        return ResponseEntity.ok(response);
    }


    // 아이디 중복 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkDuplicateId(@RequestParam String username) {
        boolean exists = memberRepository.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        Optional<Member> optionalMember = memberRepository.findByUsername(member.getUsername());

        if (optionalMember.isPresent()) {
            Member foundMember = optionalMember.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // 입력한 비밀번호와 데이터베이스의 비밀번호 비교
            if (passwordEncoder.matches(member.getPassword(), foundMember.getPassword())) {
                return ResponseEntity.ok("로그인 성공");
            } else {
                return ResponseEntity.badRequest().body("비밀번호가 올바르지 않습니다.");
            }
        }

        return ResponseEntity.badRequest().body("존재하지 않는 아이디입니다.");
    }
}
