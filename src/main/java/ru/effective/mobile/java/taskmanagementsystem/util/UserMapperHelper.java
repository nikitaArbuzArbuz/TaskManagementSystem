package ru.effective.mobile.java.taskmanagementsystem.util;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.UserRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;
import ru.effective.mobile.java.taskmanagementsystem.util.exceptions.UserNotFoundException;

@Component
@RequiredArgsConstructor
public class UserMapperHelper {
    private final UserRepository userRepository;

    @Named("mapUserFromId")
    public User mapUserFromId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found",
                        System.currentTimeMillis()));
    }
}
