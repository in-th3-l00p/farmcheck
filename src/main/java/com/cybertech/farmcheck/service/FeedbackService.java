package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Feedback;
import com.cybertech.farmcheck.repository.FeedbackRepository;
import com.cybertech.farmcheck.service.dto.FeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getFeedbacks() {
        return (List<Feedback>) feedbackRepository.findAll();
    }

    public Long getFeedbackCount() {
        return feedbackRepository.count();
    }

    public void addFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setEmail(feedbackDTO.getEmail());
        feedback.setContent(feedbackDTO.getContent());
        feedbackRepository.save(feedback);
    }
}
