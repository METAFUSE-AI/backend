package com.mefafuse.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "record")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId; // 변경된 ID 타입

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private String recordHistory;
    private String recordContents;
    private String recordQuestion;
    private String recordAnswer;
    private String createdAt;
    private String updatedAt;
}
