package com.example.Interview_Tracker.Feedback.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewFeedbackDTO {
    @NotBlank(message = "Feedback text is required.")
    private String feedbackText;

    @NotNull(message = "Candidate ID is required.")
    private Integer candidateId;

    @NotNull(message = "Stage ID is required.")
    private Integer stageId;

    @NotNull(message = "Interviewer ID is required.")
    private Integer interviewerId;
}
