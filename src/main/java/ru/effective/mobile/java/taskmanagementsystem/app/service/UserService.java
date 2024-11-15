package ru.effective.mobile.java.taskmanagementsystem.app.service;

import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;

public interface UserService {
    User getAuthenticatedUser();
}
