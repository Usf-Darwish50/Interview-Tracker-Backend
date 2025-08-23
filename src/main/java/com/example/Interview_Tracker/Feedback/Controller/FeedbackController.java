package com.example.Interview_Tracker.Feedback.Controller;

import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Feedback.DTO.FeedbackResponseDTO;
import com.example.Interview_Tracker.Feedback.DTO.NewFeedbackDTO;
import com.example.Interview_Tracker.Feedback.Model.Feedback;
import com.example.Interview_Tracker.Feedback.Service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interview-tracker/api/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody NewFeedbackDTO dto) {
        Feedback newFeedback = feedbackService.addFeedback(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFeedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.findAllFeedback();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable int id) {
        Feedback feedback = feedbackService.findFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

    // Corrected endpoint to return the DTO
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbackByCandidateId(@PathVariable int candidateId) {
        List<FeedbackResponseDTO> feedbacks = feedbackService.findFeedbackByCandidateId(candidateId);
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable int id, @Valid @RequestBody NewFeedbackDTO dto) {
        Feedback updatedFeedback = feedbackService.updateFeedback(id, dto);
        return ResponseEntity.ok(updatedFeedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteFeedback(@PathVariable int id) {
        feedbackService.softDeleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
    // New: Add a specific exception handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
