package ru.effective.mobile.java.taskmanagementsystem.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.CommentRepository;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.TaskRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.CommentMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.service.CommentService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;
import ru.effective.mobile.java.taskmanagementsystem.util.exceptions.TaskNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addComment(Long taskId, String text) {
        log.info("Add comment for task ID: {}", taskId);
        return commentMapper.map(commentRepository.saveAndFlush(new Comment()
                .create(text, userService.getAuthenticatedUser(), taskRepository.findById(taskId).orElseThrow(() ->
                        new TaskNotFoundException("Task not found", System.currentTimeMillis())))));
    }
}
