package com.example.Interview_Tracker.User.Repository;

import com.example.Interview_Tracker.User.Model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepo extends JpaRepository<Manager,Integer> {
    List<Manager> findByIsDeletedFalse();

    // Method to find a specific manager by ID, only if they are not deleted
    Optional<Manager> findByManagerIdAndIsDeletedFalse(int id);

    Optional<Manager> findByUsernameAndPassword(String username, String password);

}
