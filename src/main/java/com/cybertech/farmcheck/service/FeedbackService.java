package com.cybertech.farmcheck.service;

import com.cybertech.farmcheck.domain.Feedback;
import com.cybertech.farmcheck.repository.FeedbackRepository;
import com.cybertech.farmcheck.service.dto.FeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

     public Page<Feedback> getFeedbacks(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
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
