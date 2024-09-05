package com.mefafuse.backend.Controller;

import com.mefafuse.backend.Entity.Quiz;
import com.mefafuse.backend.Entity.QuizQuestion;
import com.mefafuse.backend.Entity.QuizAnswer;
import com.mefafuse.backend.Entity.UserAnswer;
import com.mefafuse.backend.Repository.QuizRepository;
import com.mefafuse.backend.Repository.QuizQuestionRepository;
import com.mefafuse.backend.Repository.QuizAnswerRepository;
import com.mefafuse.backend.Repository.UserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuizAnswerRepository quizAnswerRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    // 퀴즈 생성
    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz createdQuiz = quizRepository.save(quiz);
        return ResponseEntity.ok(createdQuiz);
    }

    // 퀴즈 질문 추가
    @PostMapping("/{quizId}/add-question")
    public ResponseEntity<QuizQuestion> addQuestion(@PathVariable Integer quizId, @RequestBody QuizQuestion question) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            question.setQuiz(quiz);
            QuizQuestion addedQuestion = quizQuestionRepository.save(question);
            return ResponseEntity.ok(addedQuestion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 퀴즈 답변 추가
    @PostMapping("/{quizId}/add-answer")
    public ResponseEntity<QuizAnswer> addAnswer(@PathVariable Integer quizId, @RequestBody QuizAnswer answer) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            answer.setQuiz(quiz);
            QuizAnswer addedAnswer = quizAnswerRepository.save(answer);
            return ResponseEntity.ok(addedAnswer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 사용자의 답변 제출
    @PostMapping("/{quizId}/submit-answer")
    public ResponseEntity<UserAnswer> submitAnswer(@PathVariable Integer quizId, @RequestBody UserAnswer userAnswer) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            Optional<QuizQuestion> questionOptional = quizQuestionRepository.findById(userAnswer.getQuestion().getQuestionId());
            if (questionOptional.isPresent()) {
                userAnswer.setQuiz(quiz);
                userAnswer.setQuestion(questionOptional.get());
                // 정답 여부를 확인하여 저장
                Optional<QuizAnswer> correctAnswerOptional = quizAnswerRepository.findById(userAnswer.getQuestion().getQuestionId());
                if (correctAnswerOptional.isPresent()) {
                    userAnswer.setCorrect(userAnswer.getUserAnswer().equals(correctAnswerOptional.get().getContent()));
                }
                UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);
                return ResponseEntity.ok(savedUserAnswer);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 퀴즈 조회
    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
        return ResponseEntity.ok(quiz);
    }

    // 모든 퀴즈 조회
    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return ResponseEntity.ok(quizzes);
    }

    // 퀴즈에 포함된 모든 질문 조회
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuizQuestion>> getQuestionsByQuizId(@PathVariable Integer quizId) {
        List<QuizQuestion> questions = quizQuestionRepository.findByQuizQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    // 퀴즈에 제출된 모든 답변 조회
    @GetMapping("/{quizId}/answers")
    public ResponseEntity<List<QuizAnswer>> getAnswersByQuizId(@PathVariable Integer quizId) {
        List<QuizAnswer> answers = quizAnswerRepository.findByQuizQuizId(quizId);
        return ResponseEntity.ok(answers);
    }

    // 사용자가 제출한 모든 답변 조회
    @GetMapping("/{quizId}/user-answers")
    public ResponseEntity<List<UserAnswer>> getUserAnswersByQuizId(@PathVariable Integer quizId) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByQuizQuizId(quizId);
        return ResponseEntity.ok(userAnswers);
    }
}
