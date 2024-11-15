package ru.effective.mobile.java.taskmanagementsystem.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

//    @GetMapping
//    public Page<Task> getTasksByUser(@RequestParam Long userId, Pageable pageable) {
//        return taskService.getTasksByAuthorOrExecutor(userId, pageable);
//    }
}
