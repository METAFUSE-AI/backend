package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer settingId;

    @OneToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private String linkedAccount;
    private String inquiryDetails;
    private Boolean withdrawalStatus;
    private String createdAt;
    private String updatedAt;

    // Getters and Setters
}