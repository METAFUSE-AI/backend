package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
}