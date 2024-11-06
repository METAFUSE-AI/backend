package com.mefafuse.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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