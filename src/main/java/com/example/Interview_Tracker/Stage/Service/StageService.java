package com.example.Interview_Tracker.Stage.Service;


import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Repository.HiringProcessRepo;
import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.Stage.Repository.StageRepo;
import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.User.Repository.InterviewerRepo;
import com.example.Interview_Tracker.enums.StageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StageService {
    @Autowired
    private StageRepo stageRepo;

    @Autowired
    private HiringProcessRepo processRepo;


    @Autowired
    private InterviewerRepo interviewerRepo;


    // Get all non-deleted stages
    public List<Stage> findAll() {
        return stageRepo.findByIsDeletedFalse();
    }

    // Add the new method to find stages by hiring process ID
    public List<Stage> findAllByProcessId(int processId) {
        return stageRepo.findByHiringProcess_ProcessId(processId);
    }

    // Update an existing stage
    @Transactional
    public Stage updateStage(int id, Stage stageDetails) {
        Stage existingStage = this.findById(id);

        if (stageDetails.getTitle() != null) {
            existingStage.setTitle(stageDetails.getTitle());
        }
        if (stageDetails.getDescription() != null) {
            existingStage.setDescription(stageDetails.getDescription());
        }

        // Handle status update logic
        if (stageDetails.getStatus() != null && stageDetails.getStatus() == StageStatus.COMPLETED) {
            // Check if the stage is already in progress before completing it
            if (existingStage.getStatus() == StageStatus.IN_PROGRESS) {
                existingStage.setStatus(StageStatus.COMPLETED);
                Stage savedStage = stageRepo.save(existingStage);
                // Move to the next stage
                moveToNextStage(savedStage.getHiringProcess().getProcessId(), savedStage.getStageOrder());
                return savedStage;
            } else {
                // If not in progress, throw an error or handle accordingly
                throw new IllegalStateException("Cannot complete a stage that is not in progress.");
            }
        } else if (stageDetails.getStatus() != null) {
            // Allow other status updates for flexibility
            existingStage.setStatus(stageDetails.getStatus());
        }

        return stageRepo.save(existingStage);
    }

    // Get a single stage by ID
    public Stage findById(int id) {
        return stageRepo.findByStageIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stage with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }


    // Create a new stage and link it to an existing process
    @Transactional
    public Stage createStage(int processId, Stage stage) {
        HiringProcess hiringProcess = processRepo.findByProcessIdAndIsDeletedFalse(processId)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring Process with id " + processId + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        stage.setHiringProcess(hiringProcess);

        List<Stage> existingStages = stageRepo.findByHiringProcess_ProcessIdOrderByStageOrderAsc(processId);

        // Assign stageOrder
        if (existingStages.isEmpty()) {
            stage.setStageOrder(1);
            stage.setStatus(StageStatus.IN_PROGRESS); // First stage is IN_PROGRESS
        } else {
            stage.setStageOrder(existingStages.size() + 1);
            stage.setStatus(StageStatus.NOT_STARTED); // Subsequent stages are NOT_STARTED
        }

        return stageRepo.save(stage);
    }


    // Soft delete a stage by ID
    @Transactional
    public void softDeleteStageById(int id) {
        Stage stage = this.findById(id);
        stage.setDeleted(true);
        stageRepo.save(stage);
    }

    @Transactional
    protected void moveToNextStage(int processId, int currentStageOrder) {
        List<Stage> nextStages = stageRepo.findByHiringProcess_ProcessIdAndStageOrderGreaterThanOrderByStageOrderAsc(processId, currentStageOrder);
        if (!nextStages.isEmpty()) {
            Stage nextStage = nextStages.get(0);
            nextStage.setStatus(StageStatus.IN_PROGRESS);
            stageRepo.save(nextStage);
        }
    }


    // New method to assign interviewers to a stage
    @Transactional
    public Stage assignInterviewersToStage(int stageId, List<Integer> interviewerIds) {
        Stage stage = findById(stageId);
        Set<Interviewer> interviewers = interviewerIds.stream()
                .map(id -> interviewerRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Interviewer with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND)))
                .collect(Collectors.toSet());

        // Clear existing assignments to replace them
        stage.getAssignedInterviewers().clear();
        stage.getAssignedInterviewers().addAll(interviewers);

        return stageRepo.save(stage);
    }
}
