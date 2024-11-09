package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    // 특정 회원과 특정 테스트에 대한 결과를 조회하는 메서드
    List<TestResult> findByMember_MemberIdAndTest_TestId(Long memberId, Long testId);

    // 특정 회원과 특정 테스트에 대한 최신 결과를 조회하는 메서드
    TestResult findTopByMember_MemberIdAndTest_TestIdOrderByTestRoundDesc(Long memberId, Long testId);
}
