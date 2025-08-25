package com.example.Interview_Tracker.Candidate.Service;

import com.example.Interview_Tracker.Candidate.DTO.CandidateAssignmentResponseDTO;
import com.example.Interview_Tracker.Candidate.DTO.CandidateDetailDTO;
import com.example.Interview_Tracker.Candidate.DTO.NewCandidateDTO;
import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.Candidate.Repository.CandidateRepo;
import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Repository.HiringProcessRepo;
import com.example.Interview_Tracker.Stage.Model.Stage;
import com.example.Interview_Tracker.Stage.Repository.StageRepo;
import com.example.Interview_Tracker.enums.CandidateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private HiringProcessRepo hiringProcessRepo;

    @Autowired
    private StageRepo stageRepo;


    @Transactional
    public Candidate addCandidate (NewCandidateDTO newCandidateDTO)
    {
        Candidate candidate = toEntity(newCandidateDTO);
        return this.candidateRepo.save(candidate);
    }

    public List<Candidate> findAll()
    {
        return this.candidateRepo.findByIsDeletedFalse();
    }

    public Candidate findById(int id) {
        return this.candidateRepo.findByCandidateIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }
// Inside your CandidateService class

    public CandidateDetailDTO findByIdWithProcessInfo(int id) {
        Candidate candidate = this.candidateRepo.findByCandidateIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        CandidateDetailDTO dto = new CandidateDetailDTO();
        dto.setCandidateId(candidate.getCandidateId());
        dto.setFullName(candidate.getFullName());
        dto.setEmail(candidate.getEmail());
        dto.setPhone(candidate.getPhone());
        dto.setAddress(candidate.getAddress());
        dto.setPosition(candidate.getPosition());
        dto.setCvUrl(candidate.getCvUrl());
        dto.setStatus(candidate.getStatus());

        // Set the process ID if it exists
        if (candidate.getHiringProcess() != null) {
            dto.setHiringProcessId(candidate.getHiringProcess().getProcessId());
        }

        return dto;
    }

    @Transactional
    public void softDeleteCandidateById(int id) {
        Candidate candidate = this.findById(id);
        candidate.setDeleted(true);
        this.candidateRepo.save(candidate);
    }

    // Updated method to assign a candidate to a process and return a DTO
    @Transactional
    public CandidateAssignmentResponseDTO assignCandidateToProcess(int candidateId, int processId) {
        Candidate candidate = findById(candidateId);
        HiringProcess process = hiringProcessRepo.findByProcessIdAndIsDeletedFalse(processId)
                .orElseThrow(() -> new ResourceNotFoundException("Hiring Process with id " + processId + " not found.", ErrorCode.RESOURCE_NOT_FOUND));

        // Find the first stage of the process
        Optional<Stage> firstStage = stageRepo.findByHiringProcess_ProcessIdOrderByStageOrderAsc(processId)
                .stream()
                .findFirst();

        if (firstStage.isEmpty()) {
            throw new IllegalStateException("The hiring process has no stages defined.");
        }

        candidate.setHiringProcess(process);
        candidate.setCurrentStage(firstStage.get());
        candidate.setStatus(CandidateStatus.IN_REVIEW);
        candidateRepo.save(candidate);

        return toDto(candidate);
    }

    // Updated method to move a candidate to the next stage and return a DTO
    @Transactional
    public CandidateAssignmentResponseDTO moveCandidateToNextStage(int candidateId) {
        Candidate candidate = findById(candidateId);
        if (candidate.getCurrentStage() == null) {
            throw new IllegalStateException("Candidate is not assigned to a stage.");
        }

        // Find the next stage in the process
        List<Stage> nextStages = stageRepo.findByHiringProcess_ProcessIdAndStageOrderGreaterThanOrderByStageOrderAsc(
                candidate.getHiringProcess().getProcessId(),
                candidate.getCurrentStage().getStageOrder()
        );

        if (nextStages.isEmpty()) {
            // No next stage, so candidate is hired
            candidate.setStatus(CandidateStatus.HIRED);
            candidate.setCurrentStage(null);
        } else {
            Stage nextStage = nextStages.get(0);
            candidate.setCurrentStage(nextStage);
            candidate.setStatus(CandidateStatus.IN_REVIEW);
        }
        candidateRepo.save(candidate);

        return toDto(candidate);
    }

    // New helper method to convert Candidate entity to CandidateAssignmentResponseDTO
    private CandidateAssignmentResponseDTO toDto(Candidate candidate) {
        CandidateAssignmentResponseDTO dto = new CandidateAssignmentResponseDTO();
        dto.setCandidateId(candidate.getCandidateId());
        dto.setFullName(candidate.getFullName());
        // Map other fields
        dto.setStatus(candidate.getStatus());
        if (candidate.getHiringProcess() != null) {
            dto.setHiringProcessTitle(candidate.getHiringProcess().getTitle());
        }
        if (candidate.getCurrentStage() != null) {
            dto.setCurrentStageTitle(candidate.getCurrentStage().getTitle());
        }
        return dto;
    }


    // New method to reject a candidate
    @Transactional
    public Candidate rejectCandidate(int candidateId) {
        Candidate candidate = findById(candidateId);
        candidate.setStatus(CandidateStatus.REJECTED);
        candidate.setCurrentStage(null); // Remove from current stage
        return candidateRepo.save(candidate);
    }

    private Candidate toEntity(NewCandidateDTO dto) {
        Candidate candidate = new Candidate();
        candidate.setFullName(dto.getFullName());
        candidate.setAddress(dto.getAddress());
        candidate.setEmail(dto.getEmail());
        candidate.setPhone(dto.getPhone());
        candidate.setPosition(dto.getPosition());
        candidate.setCvUrl(dto.getResumeUrl());

        //Default initial status
        candidate.setStatus(CandidateStatus.PENDING);
        // default initial stage.
        return candidate;
    }
}