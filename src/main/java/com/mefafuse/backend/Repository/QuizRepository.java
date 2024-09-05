package com.mefafuse.backend.Repository;

import com.mefafuse.backend.Entity.Quiz;
import com.mefafuse.backend.Entity.QuizQuestion;
import com.mefafuse.backend.Entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
