package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    @ManyToOne
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;

    private Integer questionId;
    private String content;
    private Boolean quizResult;

    // Getters and Setters
}

