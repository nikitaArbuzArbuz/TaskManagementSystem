package ru.effective.mobile.java.taskmanagementsystem.util.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUnauthorizedException extends RuntimeException {
    private String message;
    private Long timestamp;

    public TaskUnauthorizedException(String message, Long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
