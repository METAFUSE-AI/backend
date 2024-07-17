package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizQuestionId;

    @ManyToOne
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;

    private String quizContent;
    private String createdAt;
    private String updatedAt;

    // Getters and Setters
}

