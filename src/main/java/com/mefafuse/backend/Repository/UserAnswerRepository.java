package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    List<UserAnswer> findByQuizQuizId(Integer quizId);
}
