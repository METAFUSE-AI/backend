package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Record;
import com.mefafuse.backend.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordRepository recordRepository;

    // 모든 기록 조회
    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordRepository.findAll();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 특정 ID의 기록 조회
    @GetMapping("/{id}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long id) {
        Optional<Record> record = recordRepository.findById(id);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 특정 멤버의 기록 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Record>> getRecordsByMemberId(@PathVariable Long memberId) {
        List<Record> records = recordRepository.findByMember_MemberId(memberId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 기록 생성
    @PostMapping("/create")
    public ResponseEntity<Record> createRecord(@RequestBody Record record) {
        Record createdRecord = recordRepository.save(record);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    // 기록 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long id, @RequestBody Record record) {
        if (!recordRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        record.setRecordId(id);
        Record updatedRecord = recordRepository.save(record);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }

    // 기록 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        if (!recordRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        recordRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}