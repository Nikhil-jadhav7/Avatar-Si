package com.avatarsi.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AIService {

    public String generateSummary(String content) {
        // Mock AI summary generation
        if (content == null || content.isEmpty()) {
            return "No content provided to summarize.";
        }
        
        // Return a mock summary
        String summary = "This document covers essential topics. Key takeaways include:\n" +
                "1. Understanding the core principles discussed.\n" +
                "2. Applying the methodologies in practical scenarios.\n" +
                "3. Reviewing the examples to solidify comprehension.\n\n" +
                "(AI Generated Short Note based on document content)";
                
        return summary;
    }

    public String generateStudyPlan(String topic, LocalDate examDate) {
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), examDate);
        if (daysBetween < 0) {
            return "The exam date has already passed!";
        } else if (daysBetween == 0) {
            return "The exam is today! Good luck! Do a quick revision of key formulas and concepts.";
        }
        
        StringBuilder plan = new StringBuilder("Customized Study Plan for " + topic + " (Exam in " + daysBetween + " days):\n\n");
        long daysForBasics = Math.max(1, daysBetween / 3);
        long daysForPractice = Math.max(1, daysBetween / 3);
        long daysForRevision = daysBetween - daysForBasics - daysForPractice;
        
        plan.append("Phase 1 (").append(daysForBasics).append(" days): Master the Basics\n");
        plan.append("- Read through foundational concepts.\n");
        plan.append("- Watch introductory videos.\n\n");
        
        plan.append("Phase 2 (").append(daysForPractice).append(" days): Intensive Practice\n");
        plan.append("- Solve practice problems.\n");
        plan.append("- Take mock tests.\n\n");
        
        plan.append("Phase 3 (").append(daysForRevision).append(" days): Final Revision\n");
        plan.append("- Review short notes and flashcards.\n");
        plan.append("- Focus on weak areas.\n");
        
        return plan.toString();
    }

    public List<String> suggestYouTubeLinks(String query) {
        // Mock YouTube link suggestions based on keywords
        List<String> links = new ArrayList<>();
        String normalizedQuery = query.toLowerCase();
        
        if (normalizedQuery.contains("java") || normalizedQuery.contains("spring")) {
            links.add("https://www.youtube.com/watch?v=bWPMSSsVdPk - Spring Boot Full Course");
            links.add("https://www.youtube.com/watch?v=grEKMHGYyns - Java Tutorial for Beginners");
        } else if (normalizedQuery.contains("math") || normalizedQuery.contains("calculus")) {
            links.add("https://www.youtube.com/watch?v=WsQQvHm4lSw - Calculus 1 Full Course");
        } else {
            links.add("https://www.youtube.com/watch?v=dQw4w9WgXcQ - Top tips for learning " + query);
            links.add("https://www.youtube.com/watch?v=3qHkcs3kG44 - " + query + " Crash Course");
        }
        return links;
    }
    
    public String searchInDocuments(String query, List<com.avatarsi.model.StudyDocument> documents) {
        // Simple mock RAG search
        StringBuilder results = new StringBuilder();
        results.append("Search results for '").append(query).append("':\n\n");
        boolean found = false;
        
        String lowerQuery = query.toLowerCase();
        for (com.avatarsi.model.StudyDocument doc : documents) {
            if (doc.getContent() != null && doc.getContent().toLowerCase().contains(lowerQuery)) {
                found = true;
                results.append("Found in document: ").append(doc.getFilename()).append("\n");
                // Extract a snippet
                int index = doc.getContent().toLowerCase().indexOf(lowerQuery);
                int start = Math.max(0, index - 50);
                int end = Math.min(doc.getContent().length(), index + 50);
                results.append("...").append(doc.getContent().substring(start, end).replace("\n", " ")).append("...\n\n");
            }
        }
        
        if (!found) {
            results.append("No matches found in your uploaded documents.");
        }
        
        return results.toString();
    }
}
