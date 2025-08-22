package com.example.Interview_Tracker.Stage.Repository;


import com.example.Interview_Tracker.Stage.Model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepo extends JpaRepository<Stage,Integer> {

    // Find all non-deleted stages
    List<Stage> findByIsDeletedFalse();

    // Find a non-deleted stage by its ID
    Optional<Stage> findByStageIdAndIsDeletedFalse(int id);

    // Find all stages for a specific hiring process
    List<Stage> findByHiringProcess_ProcessId(int processId);

    // Add this method to find all stages for a process, ordered by their stageOrder
    List<Stage> findByHiringProcess_ProcessIdOrderByStageOrderAsc(int processId);

    // Add this method to find stages with a higher stageOrder for the same process
    List<Stage> findByHiringProcess_ProcessIdAndStageOrderGreaterThanOrderByStageOrderAsc(int processId, int stageOrder);
}

