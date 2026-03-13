package com.giorgi.ibmapp.integration;

import java.util.List;

public class HuggingFaceRequest {

    private String model;
    private List<HuggingFaceMessage> messages;
    private double temperature;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<HuggingFaceMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<HuggingFaceMessage> messages) {
        this.messages = messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}