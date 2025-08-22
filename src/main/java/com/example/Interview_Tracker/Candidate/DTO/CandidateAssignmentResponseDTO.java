package com.example.Interview_Tracker.Candidate.DTO;


import com.example.Interview_Tracker.enums.CandidateStatus;
import lombok.Data;

@Data
public class CandidateAssignmentResponseDTO {
    private int candidateId;
    private String fullName;
    private String position;
    private CandidateStatus status;
    private String hiringProcessTitle;
    private String currentStageTitle;
}
