package com.medportal.lab_report_service.controller;

import com.medportal.lab_report_service.service.GcsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lab-reports")
public class LabReportController {

    private final GcsService gcsService;

    public LabReportController(GcsService gcsService) {
        this.gcsService = gcsService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadReport(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = gcsService.uploadFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("message", "File uploaded successfully!");
            response.put("fileUrl", fileUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
