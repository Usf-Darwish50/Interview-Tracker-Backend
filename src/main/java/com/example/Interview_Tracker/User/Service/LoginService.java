package com.example.Interview_Tracker.User.Service;

import com.example.Interview_Tracker.User.Model.Interviewer;
import com.example.Interview_Tracker.User.Model.Manager;
import com.example.Interview_Tracker.User.Repository.InterviewerRepo;
import com.example.Interview_Tracker.User.Repository.ManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private InterviewerRepo interviewerRepo;

    @Autowired
    private ManagerRepo managerRepo;

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> response = new HashMap<>();

        // Check for an interviewer first
        Optional<Interviewer> interviewer = interviewerRepo.findByUsernameAndPassword(username, password);
        if (interviewer.isPresent()) {
            response.put("success", true);
            response.put("user", interviewer.get());
            return response;
        }

        // If not an interviewer, check for a manager
        Optional<Manager> manager = managerRepo.findByUsernameAndPassword(username, password);
        if (manager.isPresent()) {
            response.put("success", true);
            response.put("user", manager.get());
            return response;
        }

        // If no user is found
        response.put("success", false);
        response.put("message", "Invalid username or password.");
        return response;
    }
}