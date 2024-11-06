package  com.mefafuse.backend.Entity;

import jakarta.persistence.*;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private Member member;

    private String recordHistory;
    private String recordContents;
    private String recordQuestion;
    private String recordAnswer;
    private String createdAt;
    private String updatedAt;

    // Getters and Setters
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getRecordHistory() {
        return recordHistory;
    }

    public void setRecordHistory(String recordHistory) {
        this.recordHistory = recordHistory;
    }

    public String getRecordContents() {
        return recordContents;
    }

    public void setRecordContents(String recordContents) {
        this.recordContents = recordContents;
    }

    public String getRecordQuestion() {
        return recordQuestion;
    }

    public void setRecordQuestion(String recordQuestion) {
        this.recordQuestion = recordQuestion;
    }

    public String getRecordAnswer() {
        return recordAnswer;
    }

    public void setRecordAnswer(String recordAnswer) {
        this.recordAnswer = recordAnswer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
