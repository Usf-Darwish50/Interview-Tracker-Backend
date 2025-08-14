package com.example.Interview_Tracker.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    VALIDATION_ERROR("E001", "Validation Error"),
    RESOURCE_NOT_FOUND("E002", "Resource Not Found");

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;

    }
}
