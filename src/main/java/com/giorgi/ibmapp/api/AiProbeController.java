package com.giorgi.ibmapp.api;

import com.giorgi.ibmapp.integration.AiTicketDraft;
import com.giorgi.ibmapp.integration.HuggingFaceInferenceClient;
import com.giorgi.ibmapp.service.CommentTriageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiProbeController {

private final CommentTriageService triageService;
    public AiProbeController(CommentTriageService triageService) {
        this.triageService = triageService;
    }

    @GetMapping("/ai-test")
    public AiTicketDraft testAi(@RequestParam String text) {
        return triageService.analyzeComment(text);
    }
}