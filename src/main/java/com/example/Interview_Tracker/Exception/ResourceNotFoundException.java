package com.example.Interview_Tracker.Exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{


    private final String code;

    public ResourceNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.code = errorCode.getCode();
    }
}
