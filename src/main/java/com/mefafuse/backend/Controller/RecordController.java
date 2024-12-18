package com.mefafuse.backend.Controller;
import com.mefafuse.backend.Repository.MemberRepository;
import com.mefafuse.backend.Repository.RecordRepository;
import com.mefafuse.backend.Response.ApiResponse;
import com.mefafuse.backend.Response.ErrorCode;
import com.mefafuse.backend.Response.SuccessCode;
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

    // 특정 ID의 기록 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getRecordById(@PathVariable("id") Long id) {
        Optional<Record> record = recordRepository.findById(id);
        if (!record.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.errorResponse(ErrorCode.RecordNotFound));
        }

        return ResponseEntity.ok(ApiResponse.successResponse(SuccessCode.RecordFetchSuccess, record.get()));
    }

    // 특정 멤버의 기록 조회 (선택적으로 유지 가능)
    @GetMapping("/member/{username}")
    public ResponseEntity<List<Record>> getRecordsByUsername(@PathVariable String username) {
        List<Record> records = recordRepository.findByMember_Username(username);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 기록 생성
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createRecord(@RequestBody Record record) {
        Optional<Member> memberOptional = memberRepository.findByUsername(record.getMember().getUsername());
        if (!memberOptional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.errorResponse(ErrorCode.MemberNotFound));
        }

        record.setMember(memberOptional.get());
        Record createdRecord = recordRepository.save(record);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.successResponse(SuccessCode.RecordCreationSuccess, createdRecord));
    }


    // 기록 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable("id") Long id, @RequestBody Record record) {
        if (!recordRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        record.setRecordId(id);
        Record updatedRecord = recordRepository.save(record);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }

    // 기록 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable("id") Long id) {
        if (!recordRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        recordRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}