package com.example.Interview_Tracker.Process.Service;

import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Repository.HiringProcessRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HiringProcessService {

    private final HiringProcessRepo processRepo;

    public HiringProcessService(HiringProcessRepo processRepo) {
        this.processRepo = processRepo;
    }

    // Get all processes
    public List<HiringProcess> getAllProcesses() {
        return processRepo.findAll();
    }

    // Get process by ID
    public HiringProcess getProcessById(int id) {
        return processRepo.findById(id).orElse(null);
    }

    // Create process
    public HiringProcess createProcess(HiringProcess process) {
        return processRepo.save(process);
    }

    // Update process
    public HiringProcess updateProcess(int id, HiringProcess processDetails) {
        HiringProcess existing = processRepo.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setTitle(processDetails.getTitle());
        existing.setStatus(processDetails.getStatus());
        existing.setCreatedDate(processDetails.getCreatedDate());
        existing.setManagerId(processDetails.getManagerId());

        return processRepo.save(existing);
    }

    // Soft delete
    public boolean deleteProcess(int id) {
        HiringProcess existing = processRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setDeleted(true);
            processRepo.save(existing);
            return true;
        }
        return false;
    }
}
