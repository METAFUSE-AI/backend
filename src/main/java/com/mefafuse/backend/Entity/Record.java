package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private String recordHistory;
    private String recordContents;
    private String recordQuestion;
    private String recordAnswer;
    private String createdAt;
    private String updatedAt;

    // Getters and Setters
}
