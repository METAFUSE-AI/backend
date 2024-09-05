package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
public class UserAnswer {
    public Integer getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(Integer userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public QuizQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QuizQuestion question) {
        this.question = question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userAnswerId;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion question;

    @Column(name = "user_answer") // user_answer 컬럼을 명시적으로 지정
    private String userAnswer;

    @Column(name = "is_correct") // is_correct 컬럼을 명시적으로 지정
    private Boolean isCorrect;

    // Getters and Setters
}
