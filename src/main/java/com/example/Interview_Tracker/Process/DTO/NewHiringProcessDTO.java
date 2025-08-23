package com.example.Interview_Tracker.Process.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewHiringProcessDTO {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotNull(message = "Manager ID is required.")
    private Integer managerId;
}
