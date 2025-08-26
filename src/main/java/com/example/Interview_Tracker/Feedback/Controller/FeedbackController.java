package com.example.Interview_Tracker.Feedback.Controller;

import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Feedback.DTO.FeedbackResponseDTO;
import com.example.Interview_Tracker.Feedback.DTO.NewFeedbackDTO;
import com.example.Interview_Tracker.Feedback.Model.Feedback;
import com.example.Interview_Tracker.Feedback.Service.FeedbackService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger; // Correct import for SLF4J Logger
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("interview-tracker/api/feedbacks")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;



    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> addFeedback(@Valid @RequestBody NewFeedbackDTO dto) {
        FeedbackResponseDTO newFeedback = feedbackService.addFeedback(dto);
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



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteFeedback(@PathVariable int id) {
        feedbackService.softDeleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
    // New: Add a specific exception handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
