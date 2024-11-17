package ru.effective.mobile.java.taskmanagementsystem.rest;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.effective.mobile.java.taskmanagementsystem.util.exceptions.TaskNotFoundException;
import ru.effective.mobile.java.taskmanagementsystem.util.exceptions.TaskUnauthorizedException;
import ru.effective.mobile.java.taskmanagementsystem.util.exceptions.UserNotFoundException;
import ru.effective.mobile.java.taskmanagementsystem.util.responses.ExceptionResponse;
import ru.effective.mobile.java.taskmanagementsystem.util.responses.MessageResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice("ru.effective.mobile.java.taskmanagementsystem.rest")
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<MessageResponse> handleTaskUnauthorizedException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), ex.getTimestamp()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTaskNotFoundException(TaskNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), ex.getTimestamp()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskUnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleTaskUnauthorizedException(TaskUnauthorizedException ex) {
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(), ex.getTimestamp()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MessageResponse> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageResponse> handleValidationException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse(ex.getMessage()));
    }
}
