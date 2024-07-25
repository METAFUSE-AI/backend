package com.mefafuse.backend.Entity.idclass;
import java.io.Serializable;
import java.util.Objects;

public class PersonalId implements Serializable {
    private Integer memberId;
    private Integer testIndex;
    private Integer testResultId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getTestIndex() {
        return testIndex;
    }

    public void setTestIndex(Integer testIndex) {
        this.testIndex = testIndex;
    }

    public Integer getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Integer testResultId) {
        this.testResultId = testResultId;
    }

    public PersonalId() {
    }

    public PersonalId(Integer memberId, Integer testIndex, Integer testResultId) {
        this.memberId = memberId;
        this.testIndex = testIndex;
        this.testResultId = testResultId;
    }

    // Getters, Setters, hashCode, equals methods

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
