package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    // username으로 해당 멤버의 모든 기록을 조회
    List<Record> findByMember_Username(String username);

    // 특정 recordId로 기록 조회
    Optional<Record> findById(Long recordId);

    // 특정 멤버의 기록이 존재하는지 확인 (recordId 기준)
    List<Record> findByMember_UsernameAndId(String username, Long recordId);
}
