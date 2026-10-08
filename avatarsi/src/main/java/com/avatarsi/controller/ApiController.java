package com.avatarsi.controller;

import com.avatarsi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/notes/upload")
    public ResponseEntity<Map<String, String>> uploadNote(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            documentService.processAndSave(file);
            response.put("status", "success");
            response.put("message", "File uploaded and processed successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
