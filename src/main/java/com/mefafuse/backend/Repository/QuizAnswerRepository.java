package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Integer> {
    List<QuizAnswer> findByQuizQuizId(Integer quizId);
}
