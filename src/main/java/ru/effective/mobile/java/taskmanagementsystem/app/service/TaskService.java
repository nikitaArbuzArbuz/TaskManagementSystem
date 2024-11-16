package ru.effective.mobile.java.taskmanagementsystem.app.service;

import org.springframework.data.domain.Pageable;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskDto editTask(Long taskId, TaskDto taskDto);

    List<TaskDto> getTasksByAuthorOrExecutor(Long userId, Pageable pageable);

    CommentDto addComment(Long taskId, String text);

    void deleteTask(Long taskId);

    TaskDto getTaskById(Long id);
}
