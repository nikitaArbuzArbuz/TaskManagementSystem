package ru.effective.mobile.java.taskmanagementsystem.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.TaskRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.TaskMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;
import ru.effective.mobile.java.taskmanagementsystem.util.exceptions.TaskNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        log.info("Create new task TITLE: {}", taskDto.getTitle());
        return taskMapper.map(taskRepository.saveAndFlush(taskMapper.map(taskDto)));
    }

    @Override
    public TaskDto editTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found", System.currentTimeMillis()));
        taskMapper.updateTaskFromDto(taskDto, task);
        log.info("Edit task ID: {}", taskId);
        return taskMapper.map(taskRepository.saveAndFlush(task));
    }

    @Override
    public List<TaskDto> getTasksByAuthorOrExecutor(Long userId, Pageable pageable) {
        log.info("Get tasks by user ID: {}", userId);
        return taskMapper.map(taskRepository.findAllByAuthorIdOrExecutorId(userId, userId, pageable).getContent());
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found", System.currentTimeMillis()));
        taskRepository.delete(task);
        log.info("Delete task ID: {}", taskId);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        log.info("Get task by ID: {}", id);
        return taskMapper.map(taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task not found", System.currentTimeMillis())));
    }

    @Override
    public TaskDto updateTaskStatus(Long taskId, Task.Status status) {
        verifyTaskExecutor(taskId, userService.getAuthenticatedUser().getId());
        log.info("Update task status ID: {} to {} status", taskId, status);
        return taskMapper.map(taskRepository.saveAndFlush(taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found", System.currentTimeMillis()))
                .setStatus(status)));
    }

    @Override
    public void verifyTaskExecutor(Long taskId, Long userId) {
        if (!taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found", System.currentTimeMillis()))
                .getExecutor().getId().equals(userId)) {
            throw new RuntimeException("You are not executor to manage this task");
        }
        log.info("Verify executor ID: {} for task ID: {}", userId, taskId);
    }
}
