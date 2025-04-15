package com.yourdomain.summarizer.controller;

import com.yourdomain.summarizer.model.SummarizeRequest;
import com.yourdomain.summarizer.model.SummarizeResponse;
import com.yourdomain.summarizer.model.Article;
import com.yourdomain.summarizer.service.SummarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class SummarizeController {

    private final SummarizeService summarizeService;

    @Autowired
    public SummarizeController(SummarizeService summarizeService) {
        this.summarizeService = summarizeService;
    }

    @PostMapping("/summarize")
    public ResponseEntity<SummarizeResponse> summarize(@RequestBody SummarizeRequest request) {
        try {
            List<Article> articles = summarizeService.summarizeFeed(request.getUrl());
            return ResponseEntity.ok(new SummarizeResponse(articles));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new SummarizeResponse());
        }
    }
}
