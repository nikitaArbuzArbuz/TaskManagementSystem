package ru.effective.mobile.java.taskmanagementsystem.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.MessageResponse;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskStatusUpdateDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.PostSort;
import ru.effective.mobile.java.taskmanagementsystem.app.service.CommentService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping
    public List<TaskDto> getTasksByAuthorOrExecutor(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(value = "sort", defaultValue = "TITLE_ASC") PostSort sortField,
                                                    @RequestParam("id") Long id) {
        return taskService.getTasksByAuthorOrExecutor(id, PageRequest.of(offset, limit, sortField.getSortValue()));
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long taskId, @RequestBody TaskStatusUpdateDto statusUpdateDto) {
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Status success changed!").getMessage())
                .body(taskService.updateTaskStatus(taskId, statusUpdateDto.getStatus()));
    }

    @PostMapping("/{taskId}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long taskId, @RequestBody CommentDto commentDto) {
        taskService.verifyTaskExecutor(taskId, userService.getAuthenticatedUser().getId());
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Comment success added!").getMessage())
                .body(commentService.addComment(taskId, commentDto.getText()));
    }
}
