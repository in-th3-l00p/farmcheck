package com.cybertech.farmcheck.service.dto;

import com.cybertech.farmcheck.domain.Feedback;

public class FeedbackDTO {
    private String email;
    private String content;

    public FeedbackDTO() {
    }

    public FeedbackDTO(Feedback feedback) {
        this.email = feedback.getEmail();
        this.content = feedback.getContent();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
