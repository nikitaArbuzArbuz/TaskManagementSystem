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
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    @Override
    public Task createTask(AdminTaskDto adminTaskDto) {
//        Task task = new Task();
//        User author = userRepository.findById(adminTaskDto.getAuthorId()).orElseThrow(() ->
//                new RuntimeException("User not found"));
//        User executor = userRepository.findById(adminTaskDto.getExecutorId()).orElseThrow(() ->
//                new RuntimeException("User not found"));

        Task task = taskMapper.map(adminTaskDto);

//        task.setTitle(adminTaskDto.getTitle());
//        task.setDescription(adminTaskDto.getDescription());
//        task.setStatus(Task.Status.PENDING);
//        task.setPriority(adminTaskDto.getPriority());
//        task.setAuthor(author);
//        task.setExecutor(executor);

        return taskRepository.save(task);
    }

    @Override
    public Task editTask(Long taskId, AdminTaskDto adminTaskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found"));

        taskMapper.updateTaskFromDto(adminTaskDto, task);

//        task.setTitle(adminTaskDto.getTitle());
//        task.setDescription(adminTaskDto.getDescription());
//        task.setStatus(Task.Status.PENDING);
//        task.setPriority(adminTaskDto.getPriority());
//        task.setAuthor(userRepository.findById(adminTaskDto.getAuthorId()).orElseThrow(() ->
//                new RuntimeException("User not found")));
//        task.setExecutor(userRepository.findById(adminTaskDto.getExecutorId()).orElseThrow(() ->
//                new RuntimeException("User not found")));

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

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new RuntimeException("Task not found!"));
        taskRepository.delete(task);
    }
}
