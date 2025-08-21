package com.example.Interview_Tracker.Feedback.Repository;

import com.example.Interview_Tracker.Feedback.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
}
