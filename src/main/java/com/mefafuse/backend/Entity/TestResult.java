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
    private Long testResultId;

    @ManyToOne
    @JoinColumn(name = "testId", nullable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private String resultType;
    private Integer score; // 총점
    private String createdAt;

    // 회차 정보
    private Integer testRound;

    public Integer getMetaCognitionScore() {
        return metaCognitionScore;
    }

    // 메타인지 점수 분류 (메타인식, 모니터링, 메타통제)
    private Integer metaCognitionScore;
    private Integer monitoringScore;
    private Integer metaControlScore;

}
