package ru.effective.mobile.java.taskmanagementsystem.app.service;

import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.JwtResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.LoginRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.SignupRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.UserDto;

public interface AuthorizationService {
    JwtResponse authorize(LoginRequest loginRequest);

    UserDto register(SignupRequest signupRequest);
}