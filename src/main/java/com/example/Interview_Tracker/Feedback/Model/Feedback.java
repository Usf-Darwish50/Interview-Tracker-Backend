package com.example.Interview_Tracker.Feedback.Model;

import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.User.Model.Interviewer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @Column(nullable = false, length = 1000)
    private String feedbackText;

    // العلاقة مع Candidate (Many Feedbacks -> One Candidate)
    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    // العلاقة مع Interviewer (Many Feedbacks -> One Interviewer)
    @ManyToOne
    @JoinColumn(name = "interviewer_id", nullable = false)
    private Interviewer interviewer;
}
