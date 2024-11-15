package ru.effective.mobile.java.taskmanagementsystem.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.*;
import ru.effective.mobile.java.taskmanagementsystem.app.service.AuthorizationService;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authorizationService.authorize(loginRequest);
        return ResponseEntity.ok()
                .header("Server",
                        new MessageResponse("Login succeeded").getMessage())
                .body(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        UserDto user = authorizationService.register(signUpRequest);
        return ResponseEntity.ok()
                .header("Server",
                        new MessageResponse("User registered successfully!").getMessage())
                .body(user);
    }
}
