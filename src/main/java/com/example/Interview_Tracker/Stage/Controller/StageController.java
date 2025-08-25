package com.example.Interview_Tracker.Stage.Controller;


import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.Stage.Service.StageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interview-tracker/api/processes/{processId}/stages")
public class StageController {

    @Autowired
    private StageService stageService;

    @PostMapping
    public ResponseEntity<Stage> addNewStage(@PathVariable int processId, @Valid @RequestBody Stage newStage) {
        Stage savedStage = stageService.createStage(processId, newStage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStage);
    }

// Move to process controller
//    @GetMapping
//    public ResponseEntity<List<Stage>> findAllStagesForProcess(@PathVariable int processId) {
//
//        List<Stage> stages = stageService.findAllStagesByProcessId(processId);
//        return ResponseEntity.ok(stages);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Stage> findStageById(@PathVariable int id) {
        Stage stage = stageService.findById(id);
        return ResponseEntity.ok(stage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@PathVariable int id, @Valid @RequestBody Stage stageDetails) {
        Stage updatedStage = stageService.updateStage(id, stageDetails);
        return ResponseEntity.ok(updatedStage);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteStageById(@PathVariable int id) {
        stageService.softDeleteStageById(id);
        return ResponseEntity.noContent().build();
    }


    // New endpoint to assign an interviewer to a stage
    @PutMapping("/{stageId}/assign-interviewer/{interviewerId}")
    public ResponseEntity<Stage> assignInterviewerToStage(@PathVariable int stageId, @PathVariable int interviewerId) {
        Stage updatedStage = stageService.assignInterviewerToStage(stageId, interviewerId);
        return ResponseEntity.ok(updatedStage);
    }
}
