package com.example.Interview_Tracker.Candidate.DTO;

import com.example.Interview_Tracker.enums.CandidateStatus;
import lombok.Data;

@Data
public class CandidateResponseDTO {
    private int candidateId;
    private String fullName;
    private String email;
    private String phone;
    private String position;
    private CandidateStatus status;
    private String cvUrl;
}
