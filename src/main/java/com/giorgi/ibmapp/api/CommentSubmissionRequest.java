package com.giorgi.ibmapp.api;

import jakarta.validation.constraints.NotBlank;

public class CommentSubmissionRequest {

    @NotBlank
    private String authorHandle;

    @NotBlank
    private String body;

    public String getAuthorHandle() {
        return authorHandle;
    }
    public void setAuthorHandle(String authorHandle) {
        this.authorHandle = authorHandle;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

}
