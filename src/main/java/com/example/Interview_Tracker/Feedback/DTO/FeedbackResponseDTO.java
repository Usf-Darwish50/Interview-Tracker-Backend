package com.example.Interview_Tracker.Feedback.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {

    private String feedbackText;
    private String candidateName;
    private String stageTitle;
    private String processTitle;
    private String interviewerName;
}
