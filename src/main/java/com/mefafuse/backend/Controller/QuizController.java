package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Quiz;
import com.mefafuse.backend.Entity.QuizAnswerRequest;
import com.mefafuse.backend.Entity.QuizUserAnswer;
import com.mefafuse.backend.Repository.QuizRepository;
import com.mefafuse.backend.Repository.QuizUserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizUserAnswerRepository quizUserAnswerRepository;

    // 퀴즈 목록 조회 API
    @GetMapping("/random")
    public List<Quiz> getRandomQuizzes(@RequestParam int count) {
        List<Quiz> allQuizzes = quizRepository.findAll();
        Collections.shuffle(allQuizzes);
        return allQuizzes.subList(0, count); // 랜덤 퀴즈 반환
    }

    // 퀴즈 정답 제출 API
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuizAnswer(@RequestBody QuizAnswerRequest request) {
        Optional<Quiz> quizOptional = quizRepository.findById(request.getQuizId());
        if (!quizOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid quiz ID");
        }

        Quiz quiz = quizOptional.get();
        boolean isCorrect = quiz.getAnswer() == request.getSelectedOption();

        QuizUserAnswer userAnswer = new QuizUserAnswer();
        userAnswer.setQuiz(quiz);
        userAnswer.setUserId(request.getUserId());
        userAnswer.setSelectedOption(request.getSelectedOption());
        userAnswer.setCorrect(isCorrect);

        quizUserAnswerRepository.save(userAnswer);

        return ResponseEntity.ok("Answer submitted successfully");
    }
}

