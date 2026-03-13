package com.giorgi.ibmapp.api;

import com.giorgi.ibmapp.integration.HuggingFaceInferenceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiProbeController {

    private final HuggingFaceInferenceClient client;

    public AiProbeController(HuggingFaceInferenceClient client) {
        this.client = client;
    }

    @GetMapping("/ai-test")
    public String testAi(@RequestParam String text) {
        return client.analyzeComment(text);
    }
}