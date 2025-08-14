package com.example.Interview_Tracker.Candidate.Model;

import com.example.Interview_Tracker.enums.CandidateStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@Table(name = "candidates")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE candidates SET is_deleted = true WHERE candidate_id = ?")
public class Candidate {

    @Id
    @SequenceGenerator(initialValue = 500,allocationSize = 1, sequenceName = "id_seq", name = "id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_seq")
    @Column(name = "candidate_id")
    private int candidateId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "position", length = 50)
    private String position;

    @Column(name = "cv_url")
    private String cvUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CandidateStatus status;


    //Foreign key from stages
    @Column(name = "current_stage_id")
    private int currentStageId;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
