package com.mefafuse.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "test_result")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private int score;  // 테스트 총점

    // 점수 관련 필드 추가
    private int metaCognitionScore;
    private int monitoringScore;
    private int metaControlScore;

    private String createdAt;  // 생성일

    private int testRound;  // 테스트 진행 회차 (round)
}
