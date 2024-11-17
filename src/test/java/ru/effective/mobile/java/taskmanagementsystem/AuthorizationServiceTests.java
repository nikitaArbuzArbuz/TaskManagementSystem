package ru.effective.mobile.java.taskmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.UserRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.JwtResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.LoginRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.SignupRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.UserDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.UserMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.service.impl.AuthorizationServiceImpl;
import ru.effective.mobile.java.taskmanagementsystem.app.service.impl.UserDetailsImpl;
import ru.effective.mobile.java.taskmanagementsystem.util.JwtUtils;
import ru.effective.mobile.java.taskmanagementsystem.util.strategy.roles.RoleStrategy;
import ru.effective.mobile.java.taskmanagementsystem.util.strategy.roles.RoleStrategyFactory;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTests {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleStrategyFactory roleStrategyFactory;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    private LoginRequest loginRequest;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("testUser@test.com", "testPassword");
        userDetails = new UserDetailsImpl(1L, "testUser", "testUser@test.com",
                "testPassword", Collections.emptyList());
    }

    @Test
    void authorizeShouldReturnJwtResponseWhenAuthenticationIsSuccessful() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("jwt.token.here");

        JwtResponse response = authorizationService.authorize(loginRequest);

        assertNotNull(response);
        assertEquals("jwt.token.here", response.getToken());
        assertEquals("testUser@test.com", response.getEmail());
    }

    @Test
    void registerShouldRegisterUserSuccessfully() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("newUser");
        signUpRequest.setEmail("newUser@test.com");
        signUpRequest.setPassword("password");
        signUpRequest.setRole(Collections.singleton("user"));

        when(userRepository.existsByLogin(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(roleStrategyFactory.getStrategy("user")).thenReturn(mock(RoleStrategy.class));
        when(userMapper.map(any(User.class))).thenReturn(new UserDto());

        UserDto clientDto = authorizationService.register(signUpRequest);

        verify(userRepository).saveAndFlush(any(User.class));
        assertNotNull(clientDto);
    }

    @Test
    void registerShouldThrowExceptionWhenUsernameIsTaken() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("existingUser");
        when(userRepository.existsByLogin(signUpRequest.getUsername())).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                authorizationService.register(signUpRequest));

        assertEquals("Error: Username is already taken!", thrown.getMessage());
    }

    @Test
    void registerShouldThrowExceptionWhenEmailIsTaken() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("existingEmail@test.com");
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                authorizationService.register(signUpRequest));

        assertEquals("Error: Email is already in use!", thrown.getMessage());
    }
}
