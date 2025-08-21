package com.example.Interview_Tracker.Feedback.Service;

import com.example.Interview_Tracker.Feedback.Model.Feedback;
import com.example.Interview_Tracker.Feedback.Repository.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
@Autowired
private FeedbackRepo feedbackRepo;

    // Get All Feedbacks
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

    // Get Feedback By ID
    public Feedback getFeedbackById(Long id) {
        return feedbackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
    }

    // Add Feedback
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }

    // Update Feedback
    public Feedback updateFeedback(Long id, Feedback updatedFeedback) {
        Feedback feedback = getFeedbackById(id);
        feedback.setFeedbackText(updatedFeedback.getFeedbackText());
        feedback.setCandidate(updatedFeedback.getCandidate());
        feedback.setInterviewer(updatedFeedback.getInterviewer());
        return feedbackRepo.save(feedback);
    }

    // Delete Feedback
    public void deleteFeedback(Long id) {
        feedbackRepo.deleteById(id);
    }
}
