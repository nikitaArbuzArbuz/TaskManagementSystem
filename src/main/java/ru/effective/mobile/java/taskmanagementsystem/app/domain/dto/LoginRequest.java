package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Please provide a username")
    private String username;

    @NotBlank(message = "Please provide a password")
    private String password;
}
