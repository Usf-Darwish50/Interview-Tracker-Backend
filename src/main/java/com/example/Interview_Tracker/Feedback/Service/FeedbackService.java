package com.example.Interview_Tracker.Feedback.Service;


import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.Candidate.Repository.CandidateRepo;
import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Feedback.DTO.FeedbackResponseDTO;
import com.example.Interview_Tracker.Feedback.DTO.NewFeedbackDTO;
import com.example.Interview_Tracker.Feedback.Model.Feedback;
import com.example.Interview_Tracker.Feedback.Repository.FeedbackRepo;
import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.Stage.Repository.StageRepo;
import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.User.Repository.InterviewerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private StageRepo stageRepo;

    @Autowired
    private InterviewerRepo interviewerRepo;

    @Transactional
    public Feedback addFeedback(NewFeedbackDTO dto) {
        Candidate candidate = candidateRepo.findById(dto.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + dto.getCandidateId() + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        Stage stage = stageRepo.findById(dto.getStageId())
                .orElseThrow(() -> new ResourceNotFoundException("Stage with id " + dto.getStageId() + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        Interviewer interviewer = interviewerRepo.findById(dto.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer with id " + dto.getInterviewerId() + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        Feedback feedback = new Feedback();
        feedback.setFeedbackText(dto.getFeedbackText());
        feedback.setCandidate(candidate);
        feedback.setStage(stage);
        feedback.setInterviewer(interviewer);

        return feedbackRepo.save(feedback);
    }


    public List<Feedback> findAllFeedback() {
        return feedbackRepo.findByIsDeletedFalse();
    }

    public Feedback findFeedbackById(int id) {
        return feedbackRepo.findByFeedbackIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found with id " + id, ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional // Ensures the session remains open to fetch all nested data
    public List<FeedbackResponseDTO> findFeedbackByCandidateId(int candidateId) {
        List<Feedback> feedbacks = feedbackRepo.findByCandidate_CandidateIdAndIsDeletedFalse(candidateId);

        if (feedbacks.isEmpty()) {
            throw new ResourceNotFoundException("No feedback found for candidate with id " + candidateId, ErrorCode.RESOURCE_NOT_FOUND);
        }

        // Map the list of Feedback entities to a list of FeedbackResponseDTOs
        return feedbacks.stream()
                .map(feedback -> new FeedbackResponseDTO(
                        feedback.getFeedbackText(),
                        feedback.getCandidate().getFullName(),
                        feedback.getStage().getTitle(),
                        feedback.getStage().getHiringProcess().getTitle(), // Access the process title
                        feedback.getInterviewer().getUsername()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Feedback updateFeedback(int id, NewFeedbackDTO dto) {
        Feedback feedback = findFeedbackById(id);
        feedback.setFeedbackText(dto.getFeedbackText());
        return feedbackRepo.save(feedback);
    }

    @Transactional
    public void softDeleteFeedback(int id) {
        Feedback feedback = findFeedbackById(id);
        feedback.setDeleted(true);
        feedbackRepo.save(feedback);
    }

}
