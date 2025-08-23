package com.example.Interview_Tracker.Feedback.Repository;


import com.example.Interview_Tracker.Feedback.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {


    List<Feedback> findByIsDeletedFalse();
    Optional<Feedback> findByFeedbackIdAndIsDeletedFalse(int id);
    List<Feedback> findByCandidate_CandidateIdAndIsDeletedFalse(int candidateId);
}
