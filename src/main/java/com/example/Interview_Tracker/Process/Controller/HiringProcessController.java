package com.example.Interview_Tracker.Process.Controller;

import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.Process.DTO.HiringProcessDTO;
import com.example.Interview_Tracker.Process.DTO.NewHiringProcessDTO;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Service.HiringProcessService;
import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.Stage.Service.StageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interview-tracker/api/processes")
public class HiringProcessController {

    @Autowired
    private HiringProcessService hiringProcessService;
    @Autowired
    private StageService stageService;

    @PostMapping
    public ResponseEntity<HiringProcess> addNewHiringProcess(@Valid @RequestBody NewHiringProcessDTO dto) {
        HiringProcess newProcess = hiringProcessService.createProcess(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProcess);
    }

    @GetMapping
    public ResponseEntity<List<HiringProcessDTO>> findAllHiringProcesses() {
        List<HiringProcessDTO> hiringProcesses = hiringProcessService.findAll();
        return ResponseEntity.ok(hiringProcesses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HiringProcessDTO> findHiringProcessById(@PathVariable int id) {
        HiringProcessDTO hiringProcess = hiringProcessService.findById(id);
        return ResponseEntity.ok(hiringProcess);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HiringProcessDTO> updateHiringProcess(@PathVariable int id, @Valid @RequestBody HiringProcess hiringProcessDetails) {
        HiringProcess updatedHiringProcess = hiringProcessService.updateProcess(id, hiringProcessDetails);
        HiringProcessDTO dto = convertToDto(updatedHiringProcess);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteHiringProcessById(@PathVariable int id) {
        hiringProcessService.softDeleteProcessById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/end/{id}")
    public ResponseEntity<Void> endProcess(@PathVariable int id) {
        hiringProcessService.endProcessById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{processId}/candidates")
    public ResponseEntity<List<Candidate>> getCandidatesByProcessId(@PathVariable int processId) {
        List<Candidate> candidates = hiringProcessService.findCandidatesByProcessId(processId);
        return ResponseEntity.ok(candidates);
    }

    // New endpoint to get the count of candidates in a process
    @GetMapping("/{processId}/candidates/count")
    public ResponseEntity<Long> getCandidateCountByProcessId(@PathVariable int processId) {
        long count = hiringProcessService.countCandidatesByProcessId(processId);
        return ResponseEntity.ok(count);
    }


    // FIX: Add a new endpoint to get all stages for a process
    @GetMapping("/{processId}/stages")
    public ResponseEntity<List<Stage>> getStagesByProcessId(@PathVariable int processId) {
        List<Stage> stages = stageService.findAllStagesByProcessId(processId);
        return ResponseEntity.ok(stages);
    }
    private HiringProcessDTO convertToDto(HiringProcess entity) {
        HiringProcessDTO dto = new HiringProcessDTO();
        dto.setProcessId(entity.getProcessId());
        dto.setTitle(entity.getTitle());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getManager().getUsername());
        return dto;
    }
}