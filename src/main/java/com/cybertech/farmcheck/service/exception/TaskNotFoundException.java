package com.cybertech.farmcheck.service.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(Long taskId) {
        super(String.format("Task %d not found.", taskId));
    }
}
