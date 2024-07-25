package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testResultId;

    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private String resultType;
    private Integer score;
    private String createdAt;
    private String updatedAt;

    // Getters and Setters
}
