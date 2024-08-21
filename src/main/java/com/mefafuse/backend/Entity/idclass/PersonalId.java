package com.mefafuse.backend.Entity.idclass;

import java.io.Serializable;
import java.util.Objects;

public class PersonalId implements Serializable {
    private Long memberId; // Integer -> Long 변경
    private Integer testIndex;
    private Long testResultId; // Integer -> Long 변경

    public PersonalId() {
    }

    public PersonalId(Long memberId, Integer testIndex, Long testResultId) {
        this.memberId = memberId;
        this.testIndex = testIndex;
        this.testResultId = testResultId;
    }

    // Getters, Setters, hashCode, equals methods
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getTestIndex() {
        return testIndex;
    }

    public void setTestIndex(Integer testIndex) {
        this.testIndex = testIndex;
    }

    public Long getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Long testResultId) {
        this.testResultId = testResultId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalId that = (PersonalId) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(testIndex, that.testIndex) &&
                Objects.equals(testResultId, that.testResultId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, testIndex, testResultId);
    }
}
