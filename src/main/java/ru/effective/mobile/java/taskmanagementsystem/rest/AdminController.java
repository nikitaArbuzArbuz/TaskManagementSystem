package ru.effective.mobile.java.taskmanagementsystem.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.AdminTaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.MessageResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.PostSort;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AdminController {
    private final TaskService taskService;

    @PutMapping
    public ResponseEntity<Task> createTask(@RequestBody AdminTaskDto adminTaskDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Task created!").getMessage())
                .body(taskService.createTask(adminTaskDto));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<Task> editTask(@PathVariable Long id, @RequestBody AdminTaskDto adminTaskDto) {
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task executor updated!").getMessage())
                .body(taskService.editTask(id, adminTaskDto));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Comment success added!").getMessage())
                .body(taskService.addComment(id, commentDto.getText()));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task success deleted!").getMessage())
                .build();
    }

    @GetMapping("/{id}/task")
    public List<AdminTaskDto> getTasksByAuthorOrExecutor(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                         @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                         @RequestParam(value = "sort", defaultValue = "TITLE_ASC") PostSort sortField,
                                                         @PathVariable Long id) {
        return taskService.getTasksByAuthorOrExecutor(id, PageRequest.of(offset, limit, sortField.getSortValue()));
    }
}
