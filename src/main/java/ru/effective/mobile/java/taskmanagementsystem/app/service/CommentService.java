package ru.effective.mobile.java.taskmanagementsystem.app.service;

import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;

public interface CommentService {
    CommentDto addComment(Long taskId, String text);
}
