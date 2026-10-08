package com.avatarsi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class StudyDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private String content; // Extracted text content

    @Lob
    private String summary; // AI-generated short notes

    private LocalDateTime uploadedAt;

    public StudyDocument(String filename, String content, String summary) {
        this.filename = filename;
        this.content = content;
        this.summary = summary;
        this.uploadedAt = LocalDateTime.now();
    }
}
