package com.example.Interview_Tracker.Candidate.Service;

import com.example.Interview_Tracker.Candidate.DTO.NewCandidateDTO;
import com.example.Interview_Tracker.Candidate.Model.Candidate;
import com.example.Interview_Tracker.Candidate.Repository.CandidateRepo;
import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.enums.CandidateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepo candidateRepo;


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


    @Transactional
    public void softDeleteCandidateById(int id) {
        Candidate candidate = this.findById(id);
        candidate.setDeleted(true);
        this.candidateRepo.save(candidate);
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
