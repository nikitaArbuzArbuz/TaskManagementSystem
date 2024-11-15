package ru.effective.mobile.java.taskmanagementsystem.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.AdminTaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(AdminTaskDto adminTaskDto);

    Task editTask(Long taskId, AdminTaskDto adminTaskDto);

    Task updateTaskStatus(Long taskId, Task.Status status);

    List<AdminTaskDto> getTasksByAuthorOrExecutor(Long userId, Pageable pageable);

    Comment addComment(Long taskId, String text);

    void deleteTask(Long taskId);
}
