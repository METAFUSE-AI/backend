// MemberController 클래스
package com.mefafuse.backend.Controller;
import com.mefafuse.backend.Response.ApiResponse;
import com.mefafuse.backend.Response.ErrorCode;
import com.mefafuse.backend.Response.SuccessCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.errorResponse(ErrorCode.UsernameAlreadyExists));
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.successResponse(SuccessCode.RegisterSuccess, null));
    }

    // 아이디 중복 확인 //Boolean 반환
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<?>> checkDuplicateId(@RequestParam(name = "username") String username) {
        boolean exists = memberRepository.existsByUsername(username);
        return ResponseEntity.ok(
                ApiResponse.successResponse(SuccessCode.CheckUsernameSuccess, exists));
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody Member member) {
        Optional<Member> optionalMember = memberRepository.findByUsername(member.getUsername());
        if (!optionalMember.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.errorResponse(ErrorCode.UserNotFound));
        }

        Member foundMember = optionalMember.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(member.getPassword(), foundMember.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.errorResponse(ErrorCode.InvalidPassword));
        }

        return ResponseEntity.ok(ApiResponse.successResponse(SuccessCode.LoginSuccess, null));
    }

}
