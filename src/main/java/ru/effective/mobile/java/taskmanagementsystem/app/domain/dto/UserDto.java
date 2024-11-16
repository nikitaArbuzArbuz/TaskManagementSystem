package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Role;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    String firstName;
    String lastName;
    @Size(max = 20)
    @NotBlank(message = "Please provide a login")
    String login;
    @Size(max = 50)
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Please provide a email")
    String email;
    Set<Role> roles;
}