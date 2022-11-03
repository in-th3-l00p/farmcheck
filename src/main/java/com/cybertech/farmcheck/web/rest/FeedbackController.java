package com.cybertech.farmcheck.web.rest;

import com.cybertech.farmcheck.service.FeedbackService;
import com.cybertech.farmcheck.service.dto.FeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public List<FeedbackDTO> getFeedbacks(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return feedbackService
            .getFeedbacks(PageRequest.of(page, size))
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
