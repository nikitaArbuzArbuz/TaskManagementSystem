package ru.effective.mobile.java.taskmanagementsystem.util.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private Long timestamp;
}
