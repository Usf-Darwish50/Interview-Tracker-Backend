package com.example.Interview_Tracker.User.Controller;

import com.example.Interview_Tracker.User.Model.Manager;
import com.example.Interview_Tracker.User.Service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("interview-tracker/api/managers")

public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping
    public ResponseEntity<Manager> addNewManager (@Valid @RequestBody Manager newManager) {
        Manager savedManager = managerService.addManager(newManager);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManager);
    }

    @GetMapping
    public ResponseEntity<List<Manager>> findAllManagers() {
        List<Manager> managers = managerService.findAll();
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> findManagerById(@PathVariable int id) {
        Manager manager = managerService.findById(id);
        return ResponseEntity.ok(manager);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteManagerById(@PathVariable int id) {
        managerService.softDeleteManagerById(id);
        return ResponseEntity.noContent().build();
    }
}
