package com.example.Interview_Tracker.Feedback.Model;


import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.User.Model.Interviewer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@Table(name = "feedbacks")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE feedbacks SET is_deleted = true WHERE feedback_id = ?")
public class Feedback {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackId;

    @Column(name = "feedback_text", columnDefinition = "TEXT", nullable = false)
    private String feedbackText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    @JsonIgnore
    private Candidate candidate;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
