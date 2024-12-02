package com.todo.todolist.exception;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;

    public ApiError(String message, int status, String path) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
