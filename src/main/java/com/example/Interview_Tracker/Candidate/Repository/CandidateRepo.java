package com.example.Interview_Tracker.Candidate.Repository;

import com.example.Interview_Tracker.Candidate.Model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate,Integer> {

    List<Candidate> findByIsDeletedFalse();
    Optional<Candidate> findByCandidateIdAndIsDeletedFalse(int id);

}
