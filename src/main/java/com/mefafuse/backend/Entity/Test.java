package com.mefafuse.backend.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String question; // 질문 내용 (텍스트)
    private Date testDate;
    private int testScore; // 총점
    private Date createdAt;
    private Date updatedAt;

    // 질문 점수를 배열로 처리
    @ElementCollection
    private int[] questionScores; // 질문에 대한 점수 리스트

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public int getTestScore() {
        return testScore;
    }

    public void setTestScore(int testScore) {
        this.testScore = testScore;
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

    public int[] getQuestionScores() {
        return questionScores;
    }

    public void setQuestionScores(int[] questionScores) {
        this.questionScores = questionScores;
    }
}
