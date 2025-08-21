package com.example.Interview_Tracker.User.Repository;


import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.User.Model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewerRepo extends JpaRepository<Interviewer, Integer> {

    List<Interviewer> findByIsDeletedFalse();

    // Method to find a specific interviewer by ID, only if they are not deleted
    Optional<Interviewer> findByInterviewerIdAndIsDeletedFalse(int id);

    Optional<Object> findById(Long interviewerId);
}
