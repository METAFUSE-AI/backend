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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizId;

    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
}
