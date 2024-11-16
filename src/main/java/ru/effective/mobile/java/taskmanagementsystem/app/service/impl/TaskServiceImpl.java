package ru.effective.mobile.java.taskmanagementsystem.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.CommentRepository;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.TaskRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.CommentMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.TaskMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        return taskMapper.map(taskRepository.save(taskMapper.map(taskDto)));
    }

    @Override
    public TaskDto editTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found"));
        taskMapper.updateTaskFromDto(taskDto, task);
        return taskMapper.map(taskRepository.save(task));
    }

    @Override
    public List<TaskDto> getTasksByAuthorOrExecutor(Long userId, Pageable pageable) {
        return taskMapper.map(taskRepository.findAllByAuthorIdOrExecutorId(userId, userId, pageable).getContent());
    }

    @Override
    public CommentDto addComment(Long taskId, String text) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found"));
        Comment comment = new Comment()
                .create(text, userService.getAuthenticatedUser(), task);
        return commentMapper.map(commentRepository.save(comment));
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found!"));
        taskRepository.delete(task);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskMapper.map(taskRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Task not found!")));
    }
}
