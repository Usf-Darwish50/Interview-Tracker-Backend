package com.example.Interview_Tracker.User.Model;

import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Data
@Entity
@Table(name = "managers")

@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE managers SET is_deleted = true WHERE manager_id = ?")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private int managerId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    // Establishing the One-to-Many relationship with HiringProcess
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HiringProcess> hiringProcesses;

}