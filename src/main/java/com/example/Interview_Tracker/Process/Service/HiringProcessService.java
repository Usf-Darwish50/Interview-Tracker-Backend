package com.example.Interview_Tracker.Process.Service;

import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Process.DTO.HiringProcessDTO;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Repository.HiringProcessRepo;
import com.example.Interview_Tracker.User.Model.Manager;
import com.example.Interview_Tracker.User.Repository.ManagerRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HiringProcessService {

    private final HiringProcessRepo processRepo;
    private final ManagerRepo managerRepo;

    public HiringProcessService(HiringProcessRepo processRepo, ManagerRepo managerRepo) {

        this.processRepo = processRepo;
        this.managerRepo = managerRepo;
    }


    @Transactional
    public HiringProcess createProcess(HiringProcess process) {
        // Find the manager by ID before saving the process
        Manager manager = managerRepo.findById(process.getManager().getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found.", ErrorCode.RESOURCE_NOT_FOUND));

        process.setManager(manager); // Set the full manager object
        process.setCreatedDate(Date.from(Instant.now()));
        return processRepo.save(process);
    }

    // Get all non-deleted processes and convert them to DTOs
    public List<HiringProcessDTO> findAll() {
        return this.processRepo.findByIsDeletedFalse().stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    // Get a single process by ID and convert it to a DTO
    public HiringProcessDTO findById(int id) {
        return this.processRepo.findByProcessIdAndIsDeletedFalse(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring Process with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }

    // Update process
    @Transactional
    public HiringProcess updateProcess(int id, HiringProcess processDetails) {
        HiringProcess existingProcess = this.processRepo.findByProcessIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring Process with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        if (processDetails.getTitle() != null) {
            existingProcess.setTitle(processDetails.getTitle());
        }
        if (processDetails.getStatus() != null) {
            existingProcess.setStatus(processDetails.getStatus());
        }

        // This is important to update the manager
        if (processDetails.getManager() != null && processDetails.getManager().getManagerId() > 0) {
            Manager manager = managerRepo.findById(processDetails.getManager().getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found.", ErrorCode.RESOURCE_NOT_FOUND));
            existingProcess.setManager(manager);
        }

        return processRepo.save(existingProcess);
    }

    // Soft delete
    @Transactional
    public void softDeleteProcessById(int id) {
        HiringProcess existing = processRepo.findByProcessIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring Process with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        existing.setDeleted(true);
        processRepo.save(existing);
    }


    @Transactional
    public void softDeleteByManagerId(int managerId) {
        List<HiringProcess> processes = this.processRepo.findByManager_ManagerId(managerId);
        for (HiringProcess process : processes) {
            process.setDeleted(true);
            this.processRepo.save(process);
        }
    }

    // This is the private method that converts the entity to a DTO.
    private HiringProcessDTO toDto(HiringProcess entity) {
        HiringProcessDTO dto = new HiringProcessDTO();
        dto.setProcessId(entity.getProcessId());
        dto.setTitle(entity.getTitle());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getManager().getUsername());
        return dto;
    }
}