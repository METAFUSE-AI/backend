package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findById(Long testId); // Optional로 변경
}
