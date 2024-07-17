package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private Integer testScore;
    private String question;
    private LocalDateTime testDate;
    private String createdAt;
    private String updatedAt;

    // Getters and Setters
}
