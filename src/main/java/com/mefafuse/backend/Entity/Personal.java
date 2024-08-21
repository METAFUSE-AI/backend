package com.mefafuse.backend.Entity;

import com.mefafuse.backend.Entity.idclass.PersonalId;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(PersonalId.class)
public class Personal implements Serializable {

    @Id
    @Column(name = "MEMBER_ID")
    private Long memberId; // Integer -> Long 변경

    @Id
    @Column(name = "TEST_INDEX")
    private Integer testIndex;

    @Id
    @Column(name = "TEST_RESULT_ID")
    private Long testResultId; // Integer -> Long 변경

    // Getters and Setters
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

    // toString, equals, hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personal personal = (Personal) o;
        return Objects.equals(memberId, personal.memberId) &&
                Objects.equals(testIndex, personal.testIndex) &&
                Objects.equals(testResultId, personal.testResultId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, testIndex, testResultId);
    }
}
