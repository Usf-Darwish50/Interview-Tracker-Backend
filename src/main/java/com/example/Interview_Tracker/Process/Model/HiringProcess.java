package com.example.Interview_Tracker.Process.Model;

import com.example.Interview_Tracker.User.Model.Manager;
import com.example.Interview_Tracker.enums.ProcessStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

@Data
@Entity
@Table(name = "hiring_processes")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE hiring_processes SET is_deleted = true WHERE process_id = ?")
public class HiringProcess {

    @Id
    @SequenceGenerator(
            initialValue = 0,
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


    // Establishing the Many-to-One relationship with Manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    @JsonBackReference
    private Manager manager;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;


}
