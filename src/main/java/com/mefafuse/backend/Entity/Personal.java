package com.mefafuse.backend.Entity;

import com.mefafuse.backend.Entity.idclass.PersonalId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(PersonalId.class)
public class Personal implements Serializable {

    @Id
    @Column(name = "MEMBER_ID")
    private Integer memberId;

    @Id
    @Column(name = "TEST_INDEX")
    private Integer testIndex;

    @Id
    @Column(name = "TEST_RESULT_ID")
    private Integer testResultId;

    // Other fields and relationships can be added here
    // For example, you can map relationships to Member, Test, and TestResult entities

    // Getters and Setters
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

