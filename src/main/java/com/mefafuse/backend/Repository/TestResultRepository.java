package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
    // 특정 회원의 테스트 결과를 createdAt 기준으로 가져오는 메서드
    List<TestResult> findByMember_MemberId(Long memberId);

    List<TestResult> findByMember_MemberIdOrderByTestRoundAsc(Long memberId);
}
