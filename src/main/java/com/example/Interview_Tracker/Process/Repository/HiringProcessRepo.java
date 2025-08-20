package com.example.Interview_Tracker.Process.Repository;


import com.example.Interview_Tracker.Process.DTO.HiringProcessDTO;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HiringProcessRepo extends JpaRepository<HiringProcess, Integer> {

    // This custom query finds all processes by the manager's ID.
    List<HiringProcess> findByManager_ManagerId(int managerId);

    // Returns a single HiringProcess entity wrapped in an Optional
    Optional<HiringProcess> findByProcessIdAndIsDeletedFalse(int id);

    // Returns a list of HiringProcess entities
    List<HiringProcess> findByIsDeletedFalse();

}


