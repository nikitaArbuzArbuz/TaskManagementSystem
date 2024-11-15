package ru.effective.mobile.java.taskmanagementsystem.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.AdminTaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

public interface TaskService {
    Task createTask(AdminTaskDto adminTaskDto);

    Task assignExecutor(Long taskId, Long executorId);

    Task updateTaskStatus(Long taskId, Task.Status status);

    Page<Task> getTasksByAuthorOrExecutor(Long userId, Pageable pageable);

    Comment addComment(Long taskId, String text);
}
