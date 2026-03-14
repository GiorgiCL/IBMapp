package com.giorgi.ibmapp.service;

import com.giorgi.ibmapp.integration.HuggingFaceInferenceClient;
import org.springframework.stereotype.Service;

@Service
public class CommentTriageService {
    private final HuggingFaceInferenceClient aiClient;

    public CommentTriageService(HuggingFaceInferenceClient aiClient) {
        this.aiClient = aiClient;
    }
    public boolean shouldCreateTicket(String commentBody){
        String result = aiClient.analyzeComment(commentBody);

        if(result == null){
            return false;
        }
        return result.trim().equalsIgnoreCase("TICKET");
    }
}
