package com.giorgi.ibmapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HuggingFaceProperties {
    @Value("${hf.api.token}")
    private String apiToken;

    public String getApiToken() {
        return apiToken;
    }
}
