package com.example.Interview_Tracker.Process.Controller;

import com.example.Interview_Tracker.Process.Model.HiringProcess;
import com.example.Interview_Tracker.Process.Service.HiringProcessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processes")
public class HiringProcessController {

    private final HiringProcessService processService;

    public HiringProcessController(HiringProcessService processService) {
        this.processService = processService;
    }

    @GetMapping
    public List<HiringProcess> getAllProcesses() {
        return processService.getAllProcesses();
    }

    @GetMapping("/{id}")
    public HiringProcess getProcessById(@PathVariable int id) {
        return processService.getProcessById(id);
    }

    @PostMapping
    public HiringProcess createProcess(@RequestBody HiringProcess process) {
        return processService.createProcess(process);
    }

    @PutMapping("/{id}")
    public HiringProcess updateProcess(@PathVariable int id, @RequestBody HiringProcess processDetails) {
        return processService.updateProcess(id, processDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteProcess(@PathVariable int id) {
        return processService.deleteProcess(id) ? "Deleted successfully" : "Process not found";
    }
}

