package com.avatarsi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class StudyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    
    private LocalDate examDate;
    
    @Lob
    private String generatedPlan; // Day by day breakdown

    public StudyPlan(String topic, LocalDate examDate, String generatedPlan) {
        this.topic = topic;
        this.examDate = examDate;
        this.generatedPlan = generatedPlan;
    }
}
