package com.example.Interview_Tracker.User.Service;

import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Repository.HiringProcessRepo;
import com.example.Interview_Tracker.User.DTO.InterviewerAssignmentResponseDTO;
import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.User.Repository.InterviewerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewerService {

    @Autowired
    private InterviewerRepo interviewerRepo;

    @Autowired
    private HiringProcessRepo hiringProcessRepo;

    @Transactional
    public Interviewer addInterviewer(Interviewer interviewer) {
        return this.interviewerRepo.save(interviewer);
    }

    public List<Interviewer> findAll() {
        return this.interviewerRepo.findByIsDeletedFalse();
    }

    public Interviewer findById(int id) {
        return this.interviewerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    public void softDeleteInterviewerById(int id) {
        Interviewer interviewer = this.findById(id);
        interviewer.setDeleted(true);
        this.interviewerRepo.save(interviewer);
    }

    @Transactional
    public InterviewerAssignmentResponseDTO assignInterviewerToProcess(int interviewerId, int processId) {
        Interviewer interviewer = findById(interviewerId);
        HiringProcess hiringProcess = hiringProcessRepo.findByProcessIdAndIsDeletedFalse(processId)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring Process with id " + processId + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        interviewer.setHiringProcess(hiringProcess);
        interviewerRepo.save(interviewer);

        return toDto(interviewer);
    }

    @Transactional
    public Interviewer unassignInterviewerFromProcess(int interviewerId) {
        Interviewer interviewer = findById(interviewerId);
        interviewer.setHiringProcess(null);
        return interviewerRepo.save(interviewer);
    }

    // Helper method to convert Interviewer entity to InterviewerAssignmentResponseDTO
    private InterviewerAssignmentResponseDTO toDto(Interviewer interviewer) {
        InterviewerAssignmentResponseDTO dto = new InterviewerAssignmentResponseDTO();
        dto.setInterviewerId(interviewer.getInterviewerId());
        dto.setUsername(interviewer.getUsername());
        dto.setPosition(interviewer.getPosition());
        if (interviewer.getHiringProcess() != null) {
            dto.setAssignedProcessTitle(interviewer.getHiringProcess().getTitle());
        }
        return dto;
    }
}
