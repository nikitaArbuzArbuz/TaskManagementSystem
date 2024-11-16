package ru.effective.mobile.java.taskmanagementsystem.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.MessageResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.service.CommentService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AdminController {
    private final TaskService taskService;
    private final CommentService commentService;

    @GetMapping
    public TaskDto getTask(@RequestParam Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Task created!").getMessage())
                .body(taskService.createTask(taskDto));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<TaskDto> editTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task executor updated!").getMessage())
                .body(taskService.editTask(id, taskDto));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Comment success added!").getMessage())
                .body(commentService.addComment(id, commentDto.getText()));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task success deleted!").getMessage())
                .build();
    }
}
