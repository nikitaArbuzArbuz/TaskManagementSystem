package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Size(max = 50)
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Please provide a email")
    private String email;

    @NotNull
    private Set<String> role;

    @NotBlank(message = "Please provide a password")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;
}
