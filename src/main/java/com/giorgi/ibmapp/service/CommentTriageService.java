package com.giorgi.ibmapp.service;

import com.giorgi.ibmapp.integration.AiTicketDraft;
import com.giorgi.ibmapp.integration.HuggingFaceInferenceClient;
import org.springframework.stereotype.Service;

@Service
public class CommentTriageService {
    private final HuggingFaceInferenceClient aiClient;

    public CommentTriageService(HuggingFaceInferenceClient aiClient) {
        this.aiClient = aiClient;
    }
    public AiTicketDraft analyzeComment(String commentBody){
        return aiClient.analyzeComment(commentBody);
    }

    }
