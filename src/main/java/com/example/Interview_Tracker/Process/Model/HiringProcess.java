package com.example.Interview_Tracker.Process.Model;

import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.User.Model.Manager;
import com.example.Interview_Tracker.enums.ProcessStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "hiring_processes")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE hiring_processes SET is_deleted = true WHERE process_id = ?")
public class HiringProcess {

    @Id
    @SequenceGenerator(
            initialValue = 1,
            allocationSize = 1,
            sequenceName = "hiring_process_id_seq",
            name = "hiring_process_id_seq"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hiring_process_id_seq")
    @Column(name = "process_id")
    private int processId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ProcessStatus status;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;


    // Many-to-One relationship with Manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
     @JsonIgnore

    private Manager manager;


    // One-to-Many relationship with Stage
    @OneToMany(mappedBy = "hiringProcess", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Stage> stages;

    // Add this new list to link candidates to the process
    @OneToMany(mappedBy = "hiringProcess", fetch = FetchType.LAZY)
    private List<Candidate> candidates;

    // New relationship: One process can have many interviewers
    @OneToMany(mappedBy = "hiringProcess", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Interviewer> interviewers;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;


}
