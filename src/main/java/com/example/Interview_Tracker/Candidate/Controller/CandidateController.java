package com.example.Interview_Tracker.Candidate.Controller;

import com.example.Interview_Tracker.Candidate.DTO.CandidateAssignmentResponseDTO;
import com.example.Interview_Tracker.Candidate.DTO.NewCandidateDTO;
import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.Candidate.Service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interview-tracker/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Candidate> addNewCandidate (@Valid @RequestBody NewCandidateDTO newCandidateDTO) {
        Candidate savedCandidate = candidateService.addCandidate(newCandidateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidate);
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> findAllCandidate() {
        List<Candidate> candidates = candidateService.findAll();
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> findCandidateById(@PathVariable int id) {
        Candidate candidate = candidateService.findById(id);
        return ResponseEntity.ok(candidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteCandidateById(@PathVariable int id) {
        candidateService.softDeleteCandidateById(id);
        return ResponseEntity.noContent().build();
    }


    // Updated endpoint to return the new DTO
    @PutMapping("/{candidateId}/assign/{processId}")
    public ResponseEntity<CandidateAssignmentResponseDTO> assignCandidateToProcess(@PathVariable int candidateId, @PathVariable int processId) {
        CandidateAssignmentResponseDTO responseDTO = candidateService.assignCandidateToProcess(candidateId, processId);
        return ResponseEntity.ok(responseDTO);
    }

    // Updated endpoint to return the new DTO
    @PutMapping("/{candidateId}/next-stage")
    public ResponseEntity<CandidateAssignmentResponseDTO> moveCandidateToNextStage(@PathVariable int candidateId) {
        CandidateAssignmentResponseDTO responseDTO = candidateService.moveCandidateToNextStage(candidateId);
        return ResponseEntity.ok(responseDTO);
    }

    // New endpoint to reject a candidate
    @PutMapping("/{candidateId}/reject")
    public ResponseEntity<Candidate> rejectCandidate(@PathVariable int candidateId) {
        Candidate updatedCandidate = candidateService.rejectCandidate(candidateId);
        return ResponseEntity.ok(updatedCandidate);
    }
}