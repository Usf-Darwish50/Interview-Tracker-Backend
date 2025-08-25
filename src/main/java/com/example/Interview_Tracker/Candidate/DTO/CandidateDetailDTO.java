package com.example.Interview_Tracker.Candidate.DTO;

import com.example.Interview_Tracker.enums.CandidateStatus;
import lombok.Data;

@Data
public class CandidateDetailDTO {

    private int candidateId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String position;
    private String cvUrl;
    private CandidateStatus status;
    private Integer hiringProcessId; // Use Integer to allow null
    private Integer currentStageId; // Use Integer to allow null
}
