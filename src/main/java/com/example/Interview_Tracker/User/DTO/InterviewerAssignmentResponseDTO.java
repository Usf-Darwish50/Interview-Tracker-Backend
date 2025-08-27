package com.example.Interview_Tracker.User.DTO;


import lombok.Data;

@Data
public class InterviewerAssignmentResponseDTO   {
    private int interviewerId;
    private String username;
    private String position;
    private String assignedProcessTitle;
}
