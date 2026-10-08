package com.avatarsi.service;

import com.avatarsi.model.StudyDocument;
import com.avatarsi.repository.StudyDocumentRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocumentService {

    @Autowired
    private StudyDocumentRepository repository;

    @Autowired
    private AIService aiService;

    public StudyDocument processAndSave(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String content = "";
        
        if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
            try (PDDocument document = PDDocument.load(file.getInputStream())) {
                PDFTextStripper stripper = new PDFTextStripper();
                content = stripper.getText(document);
            }
        } else {
            // fallback to reading as text
            content = new String(file.getBytes());
        }

        // Use AI to generate a summary
        String summary = aiService.generateSummary(content);

        StudyDocument doc = new StudyDocument(filename, content, summary);
        return repository.save(doc);
    }
}
