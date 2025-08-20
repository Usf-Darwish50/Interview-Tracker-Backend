package com.example.Interview_Tracker.User.Service;

import com.example.Interview_Tracker.Exception.ErrorCode;
import com.example.Interview_Tracker.Exception.ResourceNotFoundException;
import com.example.Interview_Tracker.Process.Service.HiringProcessService;
import com.example.Interview_Tracker.User.Model.Manager;
import com.example.Interview_Tracker.User.Repository.ManagerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class    ManagerService {

    @Autowired
    private ManagerRepo managerRepo;

    @Autowired
    private HiringProcessService hiringProcessService;

    @Transactional
    public Manager addManager(Manager manager) {
        return this.managerRepo.save(manager);
    }

    public List<Manager> findAll() {
        return this.managerRepo.findByIsDeletedFalse();
    }

    public Manager findById(int id) {
        return this.managerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manager with id " + id + " not found.", ErrorCode.RESOURCE_NOT_FOUND));
    }


    @Transactional
    public void softDeleteManagerById(int id) {
        // First, soft-delete all hiring processes associated with this manager
        this.hiringProcessService.softDeleteByManagerId(id);

        // Then, soft-delete the manager
        Manager manager = this.findById(id);
        manager.setDeleted(true);
        this.managerRepo.save(manager);
    }
}
