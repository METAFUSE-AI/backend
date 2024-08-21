package com.mefafuse.backend.Controller;
import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mefafuse.backend.Entity.Record;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
@CrossOrigin(origins = "http://172.30.1.36:19006")
public class RecordController {
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Record>> getRecordsByMemberId(@PathVariable Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {
            List<Record> records = recordRepository.findByMember_MemberId(memberId);
            return ResponseEntity.ok(records);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
