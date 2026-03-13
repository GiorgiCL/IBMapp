package com.giorgi.ibmapp.integration;

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

    public HuggingFaceInferenceClient(HuggingFaceProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    public String analyzeComment(String commentText) {
        HuggingFaceRequest request = new HuggingFaceRequest();
        request.setModel(MODEL_NAME);
        request.setTemperature(0.1);
        request.setMessages(List.of(
                new HuggingFaceMessage("system",
                        "You analyze product feedback. Return only plain text. Say TICKET if the comment is a real support issue. Say NO_TICKET if it is not."),
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

        return response.getChoices().get(0).getMessage().getContent();
    }
}