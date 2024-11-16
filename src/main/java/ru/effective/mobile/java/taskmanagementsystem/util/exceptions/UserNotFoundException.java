package ru.effective.mobile.java.taskmanagementsystem.util.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
    private String message;
    private Long timestamp;

    public UserNotFoundException(String message, Long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
