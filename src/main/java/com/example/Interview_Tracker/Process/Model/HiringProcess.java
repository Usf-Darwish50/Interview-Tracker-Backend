package com.example.Interview_Tracker.Process.Model;

import com.example.Interview_Tracker.enums.ProcessStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

@Data
@Entity
@Table(name = "hiring_processes")
@SQLRestriction("is_deleted = false")
public class HiringProcess {

    @Id
    @SequenceGenerator(
            initialValue = 500,
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

    // Foreign key from managers table
    @Column(name = "manager_id")
    private int managerId;

    @Column(name = "is_deleted")
    private boolean Deleted = false;


}
