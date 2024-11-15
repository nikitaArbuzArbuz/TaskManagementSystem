package ru.effective.mobile.java.taskmanagementsystem.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.CommentRepository;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.TaskRepository;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.UserRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.AdminTaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public Task createTask(AdminTaskDto adminTaskDto) {
        Task task = new Task();
        User author = userRepository.findById(adminTaskDto.getAuthorId()).orElseThrow(() ->
                new RuntimeException("User not found"));
        User executor = userRepository.findById(adminTaskDto.getExecutorId()).orElseThrow(() ->
                new RuntimeException("User not found"));

        task.setTitle(adminTaskDto.getTitle());
        task.setDescription(adminTaskDto.getDescription());
        task.setStatus(Task.Status.PENDING);
        task.setPriority(adminTaskDto.getPriority());
        task.setAuthor(author);
        task.setExecutor(executor);

        return taskRepository.save(task);
    }

    @Override
    public Task assignExecutor(Long taskId, Long executorId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found"));
        User executor = userRepository.findById(executorId).orElseThrow(() ->
                new RuntimeException("User not found"));
        task.setExecutor(executor);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskStatus(Long taskId, Task.Status status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getTasksByAuthorOrExecutor(Long userId, Pageable pageable) {
        return taskRepository.findAllByAuthorIdOrExecutorId(userId, userId, pageable);
    }

    @Override
    public Comment addComment(Long taskId, String text) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found"));
        Comment comment = new Comment();
        comment.setText(text);
        comment.setTask(task);
        comment.setAuthor(userService.getAuthenticatedUser());
        return commentRepository.save(comment);
    }
}
