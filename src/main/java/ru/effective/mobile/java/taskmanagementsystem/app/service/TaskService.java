package ru.effective.mobile.java.taskmanagementsystem.app.service;

import org.springframework.data.domain.Pageable;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskDto editTask(Long taskId, TaskDto taskDto);

    List<TaskDto> getTasksByAuthorOrExecutor(Long userId, Pageable pageable);

    void deleteTask(Long taskId);

    TaskDto getTaskById(Long id);

    TaskDto updateTaskStatus(Long taskId, Task.Status status);

    void verifyTaskExecutor(Long taskId, Long userId);
}
