package com.giorgi.ibmapp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giorgi.ibmapp.config.HuggingFaceProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class HuggingFaceInferenceClient {

    private static final String API_URL = "https://router.huggingface.co/v1/chat/completions";
    private static final String MODEL_NAME = "Qwen/Qwen2.5-7B-Instruct";

    private final HuggingFaceProperties properties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HuggingFaceInferenceClient(HuggingFaceProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public AiTicketDraft analyzeComment(String commentText){
        HuggingFaceRequest request = new HuggingFaceRequest();
        request.setModel(MODEL_NAME);
        request.setTemperature(0.1);
        request.setMessages(List.of(
                new HuggingFaceMessage(
                        "system",
                        """
                               You analyze product feedback for a support platform.
                               
                               Return only valid JSON with this structure :
                               {
                               "shouldCreateTicket": true,
                               "title": "short issue title",
                               "category": "BUG",
                               "priority": "HIGH",
                               "summary": "short summary"
                               }
                               Rules:
                               1. shouldCreateTicket must be true only for real support issues
                               2. category must be one of : BUG, FEATURE, BILLING, ACCOUNT, OTHER
                               3.priority must be one of: LOW, MEDIUM, HIGH
                               4. if shouldCreateTicket is false, still return valid JSON and use:
                               title = "No ticket needed"
                               category = "OTHER"
                               priority = "LOW"
                               summary = "This comment does not require ticket creation"
                               Return JSON only. No explanation.
                               """
                ),
                new HuggingFaceMessage("user", commentText)

        ));
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(properties.getApiToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HuggingFaceRequest> entity = new HttpEntity<>(request, headers);

        HuggingFaceResponse response = restTemplate.postForObject(
                API_URL,
                entity,
                HuggingFaceResponse.class
        );

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new IllegalStateException("Empty response from Hugging Face");
        }
        String content = response.getChoices().get(0).getMessage().getContent();

        content = content.replaceAll("(?s)```json\\s*|```", "").trim();
        try {
            return objectMapper.readValue(content, AiTicketDraft.class);
        }catch(Exception exception){
            throw new IllegalStateException("Failed to parse AI response" + content,exception );
        }
    }
}