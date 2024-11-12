package com.mefafuse.backend.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getMetaCognitionScore() {
        return metaCognitionScore;
    }

    public void setMetaCognitionScore(int metaCognitionScore) {
        this.metaCognitionScore = metaCognitionScore;
    }

    public int getMonitoringScore() {
        return monitoringScore;
    }

    public void setMonitoringScore(int monitoringScore) {
        this.monitoringScore = monitoringScore;
    }

    public int getMetaControlScore() {
        return metaControlScore;
    }

    public void setMetaControlScore(int metaControlScore) {
        this.metaControlScore = metaControlScore;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @ManyToOne
    @JoinColumn(name = "username")
    private Member member;

    private int totalScore;  // 총점
    private int metaCognitionScore;  // 메타 인식 점수
    private int monitoringScore;  // 모니터링 점수
    private int metaControlScore;  // 메타 통제 점수

    private int seq;  // 해당 사용자의 테스트 순서

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getters and Setters
}
