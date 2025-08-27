
package com.example.Interview_Tracker.User.Controller;


import com.example.Interview_Tracker.User.DTO.InterviewerAssignmentResponseDTO;
import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.User.Service.InterviewerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interview-tracker/api/interviewers")
public class InterviewerController {


    @Autowired
    private InterviewerService interviewerService;

    @PostMapping
    public ResponseEntity<Interviewer> addNewInterviewer (@Valid @RequestBody Interviewer newInterviewer) {
        Interviewer savedInterviewer = interviewerService.addInterviewer(newInterviewer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInterviewer);
    }

    @GetMapping
    public ResponseEntity<List<Interviewer>> findAllInterviewers() {
        List<Interviewer> interviewers = interviewerService.findAll();
        return ResponseEntity.ok(interviewers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interviewer> findInterviewerById(@PathVariable int id) {
        Interviewer interviewer = interviewerService.findById(id);
        return ResponseEntity.ok(interviewer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteInterviewerById(@PathVariable int id) {
        interviewerService.softDeleteInterviewerById(id);
        return ResponseEntity.noContent().build();
    }

    // New endpoint to assign an interviewer to a process
    @PutMapping("/{interviewerId}/assign-process/{processId}")
    public ResponseEntity<InterviewerAssignmentResponseDTO> assignInterviewerToProcess(@PathVariable int interviewerId, @PathVariable int processId) {
        InterviewerAssignmentResponseDTO responseDTO = interviewerService.assignInterviewerToProcess(interviewerId, processId);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{interviewerId}/unassign-process")
    public ResponseEntity<Interviewer> unassignInterviewerFromProcess(@PathVariable int interviewerId) {
        Interviewer updatedInterviewer = interviewerService.unassignInterviewerFromProcess(interviewerId);
        return ResponseEntity.ok(updatedInterviewer);
    }
}
