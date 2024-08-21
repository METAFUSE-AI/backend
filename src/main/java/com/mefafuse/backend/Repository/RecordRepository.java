package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByMember_MemberId(Long memberId);
}
