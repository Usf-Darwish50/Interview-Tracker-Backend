package com.example.Interview_Tracker.Feedback.Controller;

import com.example.Interview_Tracker.Feedback.Model.Feedback;
import com.example.Interview_Tracker.Feedback.Service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Get All
    @GetMapping
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    // Get By Id
    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    // Create
    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.createFeedback(feedback);
    }

    // Update
    @PutMapping("/{id}")
    public Feedback updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        return feedbackService.updateFeedback(id, feedback);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
    }
}
