package com.example.Interview_Tracker.User.Service;

import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
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

    @Transactional
    public Interviewer addInterviewer(Interviewer interviewer) {
        return this.interviewerRepo.save(interviewer);
    }

    public List<Interviewer> findAll() {
        return this.interviewerRepo.findByIsDeletedFalse();
    }

    public Interviewer findById(int id) {
        return this.interviewerRepo.findByInterviewerIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    public void softDeleteInterviewerById(int id) {
        Interviewer interviewer = this.findById(id);
        interviewer.setDeleted(true);
        this.interviewerRepo.save(interviewer);
    }
}
