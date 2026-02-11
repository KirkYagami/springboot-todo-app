package com.nick.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Task Manager API!";
    }

    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }
}