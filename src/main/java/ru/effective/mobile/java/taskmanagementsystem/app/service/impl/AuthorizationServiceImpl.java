package ru.effective.mobile.java.taskmanagementsystem.app.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.UserRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.JwtResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.LoginRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.SignupRequest;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.UserDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Role;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.UserMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.service.AuthorizationService;
import ru.effective.mobile.java.taskmanagementsystem.util.JwtUtils;
import ru.effective.mobile.java.taskmanagementsystem.util.strategy.roles.RoleStrategyFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository clientRepository;
    private final PasswordEncoder encoder;
    private final RoleStrategyFactory roleStrategyFactory;
    private final UserMapper userMapper;

    @Override
    public JwtResponse authorize(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        log.info("Пользователь с email {} успешно аутентифицирован", userDetails.getEmail());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Transactional
    @Override
    public UserDto register(SignupRequest signUpRequest) {

        if (clientRepository.existsByLogin(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (clientRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles.isEmpty()) {
            roles.add(roleStrategyFactory.getStrategy("user").getRole());
        } else {
            strRoles.forEach(role ->
                    roles.add(roleStrategyFactory.getStrategy(role).getRole())
            );
        }

        user.setRoles(roles);
        clientRepository.saveAndFlush(user);

        log.info("Пользователь успешно зарегистрирован email {}", user.getEmail());

        return userMapper.map(user);
    }
}
