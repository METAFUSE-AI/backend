package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Record;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 모든 기록 조회
    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordRepository.findAll();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 특정 멤버의 기록 조회
    @GetMapping("/member/{username}")
    public ResponseEntity<List<Record>> getRecordsByUsername(@PathVariable String username) {
        List<Record> records = recordRepository.findByMember_Username(username);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 특정 기록 조회 (recordId 기준)
    @GetMapping("/{recordId}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long recordId) {
        Optional<Record> record = recordRepository.findById(recordId);

        if (record.isPresent()) {
            return new ResponseEntity<>(record.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 기록 생성
    @PostMapping("/create")
    public ResponseEntity<Record> createRecord(@RequestBody Record record) {
        String username = record.getMember().getUsername();
        Optional<Member> memberOptional = memberRepository.findByUsername(username);

        if (!memberOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        record.setMember(memberOptional.get());
        Record createdRecord = recordRepository.save(record);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    // 특정 기록 업데이트 (recordId 기준)
    @PutMapping("/{recordId}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long recordId, @RequestBody Record record) {
        Optional<Record> existingRecordOptional = recordRepository.findById(recordId);

        if (!existingRecordOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Record existingRecord = existingRecordOptional.get();
        record.setMember(existingRecord.getMember());  // 기존 멤버 유지
        record.setRecordId(existingRecord.getRecordId()); // 기존 recordId 유지

        Record updatedRecord = recordRepository.save(record);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }

    // 특정 기록 삭제 (recordId 기준)
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long recordId) {
        Optional<Record> record = recordRepository.findById(recordId);

        if (!record.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        recordRepository.delete(record.get());
        return ResponseEntity.noContent().build();
    }
}
