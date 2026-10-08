package com.avatarsi.controller;

import com.avatarsi.model.StudyDocument;
import com.avatarsi.model.StudyPlan;
import com.avatarsi.repository.StudyDocumentRepository;
import com.avatarsi.repository.StudyPlanRepository;
import com.avatarsi.service.AIService;
import com.avatarsi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private StudyDocumentRepository documentRepository;
    
    @Autowired
    private StudyPlanRepository studyPlanRepository;
    
    @Autowired
    private AIService aiService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/documents")
    public String documents(Model model) {
        model.addAttribute("documents", documentRepository.findAll());
        return "documents";
    }

    @PostMapping("/documents/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file, Model model) {
        try {
            documentService.processAndSave(file);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload file.");
        }
        return "redirect:/documents";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            List<StudyDocument> docs = documentRepository.findAll();
            String results = aiService.searchInDocuments(query, docs);
            model.addAttribute("results", results);
            model.addAttribute("query", query);
        }
        return "search";
    }

    @GetMapping("/study-plans")
    public String studyPlans(Model model) {
        model.addAttribute("plans", studyPlanRepository.findAll());
        return "study-plans";
    }

    @PostMapping("/study-plans/create")
    public String createPlan(@RequestParam("topic") String topic, 
                             @RequestParam("examDate") String examDateStr) {
        LocalDate examDate = LocalDate.parse(examDateStr);
        String generatedPlan = aiService.generateStudyPlan(topic, examDate);
        StudyPlan plan = new StudyPlan(topic, examDate, generatedPlan);
        studyPlanRepository.save(plan);
        return "redirect:/study-plans";
    }
    
    @GetMapping("/youtube")
    public String youtube(@RequestParam(value = "topic", required = false) String topic, Model model) {
        if (topic != null && !topic.isEmpty()) {
            model.addAttribute("links", aiService.suggestYouTubeLinks(topic));
            model.addAttribute("topic", topic);
        }
        return "youtube";
    }
}
