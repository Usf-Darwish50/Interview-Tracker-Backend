package com.example.Interview_Tracker.User.Model;

import com.example.Interview_Tracker.Stage.Model.Stage;
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

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "postion", length = 100)
    private String postion;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;



    @OneToMany(mappedBy = "interviewer")
    @JsonIgnore
    private Set<Stage> assignedStages;

}