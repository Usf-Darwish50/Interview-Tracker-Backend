package com.example.Interview_Tracker;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;

public class EnvironmentLoader {

    public static void load(String[] args) {
        Dotenv dotenv = Dotenv.load();
        for (DotenvEntry envName : dotenv.entries()) {
            System.setProperty(envName.getKey(), envName.getValue());
        }
        // This method will be called from your main class's main method
        SpringApplication.run(InterviewTrackerApplication.class, args);
    }
}