package ru.effective.mobile.java.taskmanagementsystem.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.MessageResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.AdminTaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AdminController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody AdminTaskDto adminTaskDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Task created!").getMessage())
                .body(taskService.createTask(adminTaskDto));
    }

    @PatchMapping("/{id}/executor")
    public ResponseEntity<Task> assignExecutor(@PathVariable Long id, @RequestParam Long executorId) {
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task executor updated!").getMessage())
                .body(taskService.assignExecutor(id, executorId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id, @RequestParam Task.Status status) {
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task status updated!").getMessage())
                .body(taskService.updateTaskStatus(id, status));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Comment success added!").getMessage())
                .body(taskService.addComment(id, commentDto.getText()));
    }
}
