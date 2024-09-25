package com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
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



    // Getters and Setters
    public Long getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Long testResultId) {
        this.testResultId = testResultId;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTestRound() {
        return testRound;
    }

    public void setTestRound(Integer testRound) {
        this.testRound = testRound;
    }

    public void setMetaCognitionScore(Integer metaCognitionScore) {
        this.metaCognitionScore = metaCognitionScore;
    }

    public Integer getMonitoringScore() {
        return monitoringScore;
    }

    public void setMonitoringScore(Integer monitoringScore) {
        this.monitoringScore = monitoringScore;
    }

    public Integer getMetaControlScore() {
        return metaControlScore;
    }

    public void setMetaControlScore(Integer metaControlScore) {
        this.metaControlScore = metaControlScore;
    }

}
