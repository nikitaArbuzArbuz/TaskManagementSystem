package ru.effective.mobile.java.taskmanagementsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.JwtResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.LoginRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.SignupRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.UserDto;
import ru.effective.mobile.java.taskmanagementsystem.app.service.AuthorizationService;
import ru.effective.mobile.java.taskmanagementsystem.util.responses.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "Аутентификация и регистрация пользователей")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @Operation(summary = "Авторизация пользователя",
            description = "Аутентификация пользователя с выдачей JWT токена")
    @ApiResponse(responseCode = "200", description = "Аутентификация прошла успешно",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JwtResponse.class)))
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authorizationService.authorize(loginRequest);
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Login succeeded").getMessage())
                .body(jwtResponse);
    }

    @Operation(summary = "Регистрация нового пользователя",
            description = "Регистрация нового пользователя в системе")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)))
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        UserDto user = authorizationService.register(signUpRequest);
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("User registered successfully!").getMessage())
                .body(user);
    }
}
