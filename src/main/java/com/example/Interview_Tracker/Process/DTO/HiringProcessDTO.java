package com.example.Interview_Tracker.Process.DTO;

import com.example.Interview_Tracker.enums.ProcessStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class HiringProcessDTO {
    private int processId;
    private String title;
    private ProcessStatus status;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdDate;
    private String createdBy;

}
