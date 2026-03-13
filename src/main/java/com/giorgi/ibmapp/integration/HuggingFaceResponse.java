package com.giorgi.ibmapp.integration;

import java.util.List;

public class HuggingFaceResponse {

    private List<HuggingFaceChoice> choices;

    public List<HuggingFaceChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<HuggingFaceChoice> choices) {
        this.choices = choices;
    }
}