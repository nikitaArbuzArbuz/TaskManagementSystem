package ru.effective.mobile.java.taskmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.UserRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;
import ru.effective.mobile.java.taskmanagementsystem.app.service.impl.UserServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testuser");
    }

    @Test
    void shouldGetAuthenticatedUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findByLogin("testuser")).thenReturn(java.util.Optional.of(user));

        User result = userService.getAuthenticatedUser();

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("testuser");
        verify(userRepository, times(1)).findByLogin("testuser");
    }
}

