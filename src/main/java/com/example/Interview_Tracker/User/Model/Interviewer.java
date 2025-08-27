package com.example.Interview_Tracker.User.Model;

import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.enums.Role;
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
@Table(name = "interviewers")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE interviewers SET is_deleted = true WHERE interviewer_id = ?")
public class Interviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interviewer_id")
    private int interviewerId;

    @Column(name = "username", nullable = false,unique = true, length = 100)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "position", length = 100)
    private String position;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.INTERVIEWER;

    @OneToMany(mappedBy = "interviewer")
    @JsonIgnore
    private Set<Stage> assignedStages;

    // New relationship: Many interviewers can be assigned to one process
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    @JsonIgnore
    private HiringProcess hiringProcess;
}