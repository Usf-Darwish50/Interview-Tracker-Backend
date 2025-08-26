package com.example.Interview_Tracker.Stage.Model;

import com.example.Interview_Tracker.Feedback.Model.Feedback;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.enums.StageStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "stages")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE stages SET is_deleted = true WHERE stage_id = ?")
public class Stage {


    @Id
    @SequenceGenerator(
            initialValue = 1,
            allocationSize = 1,
            sequenceName = "stage_id_seq",
            name = "stage_id_seq"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stage_id_seq")
    @Column(name = "stage_id")
    private int stageId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, length = 255)
    private String description;


    @Column(name = "stage_order", nullable = false)
    private int stageOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StageStatus status;

    // Many-to-One relationship to HiringProcess
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", nullable = false)
    @JsonIgnore
    private HiringProcess hiringProcess;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interviewer_id")
    private Interviewer interviewer;



}
