package com.example.Interview_Tracker.Process.Controller;

import com.example.Interview_Tracker.Process.DTO.HiringProcessDTO;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Service.HiringProcessService;
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

    @PostMapping
    public ResponseEntity<HiringProcessDTO> addNewHiringProcess(@Valid @RequestBody HiringProcess newHiringProcess) {
        HiringProcess savedHiringProcess = hiringProcessService.createProcess(newHiringProcess);
        HiringProcessDTO dto = convertToDto(savedHiringProcess);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
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