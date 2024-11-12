package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.Member;
import com.mefafuse.backend.Entity.Test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    // username을 기준으로 Test를 가져오는 쿼리
    List<Test> findByMember_Username(String username);

    // username을 기준으로 testId와 seq를 가져오는 쿼리
    @Query("SELECT new map(t.testId as testId, t.seq as seq) FROM Test t WHERE t.member.username = :username")
    List<Map<String, Object>> findTestIdsAndSeqByUsername(@Param("username") String username);

    // username을 기준으로 최대 seq 값을 가져오는 쿼리
    @Query("SELECT MAX(t.seq) FROM Test t WHERE t.member.username = :username")
    Integer findMaxSeqByUsername(@Param("username") String username);

    // username을 기준으로 테스트 결과 (testId, seq, createdAt) 가져오는 쿼리
    @Query("SELECT new map(t.testId as testId, t.seq as seq, t.createdAt as createdAt) FROM Test t WHERE t.member.username = :username")
    List<Map<String, Object>> findTestResultsByUsername(@Param("username") String username);
}
