package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.service.FeedbackService;
import com.cybertech.farmcheck.service.dto.FeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    private FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public List<FeedbackDTO> getFeedbacks() {
        return feedbackService.getFeedbacks()
            .stream()
            .map(FeedbackDTO::new)
            .toList();
    }

    @GetMapping("/count")
    public Long getFeedbackCount() {
        return feedbackService.getFeedbackCount();
    }

    @PostMapping
    public void addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        feedbackService.addFeedback(feedbackDTO);
    }
}
