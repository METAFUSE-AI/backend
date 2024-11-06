package com.mefafuse.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "test")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
