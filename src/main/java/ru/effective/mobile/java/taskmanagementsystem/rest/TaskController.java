package ru.effective.mobile.java.taskmanagementsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskStatusUpdateDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.PostSort;
import ru.effective.mobile.java.taskmanagementsystem.app.service.CommentService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;
import ru.effective.mobile.java.taskmanagementsystem.util.responses.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Управление задачами")
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;
    private final UserService userService;

    @Operation(summary = "Получить задачи по автору или исполнителю",
            description = "Возвращает список задач по идентификатору автора или исполнителя")
    @ApiResponse(responseCode = "200", description = "Список задач",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class)))
    @GetMapping
    public List<TaskDto> getTasksByAuthorOrExecutor(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(value = "sort", defaultValue = "TITLE_ASC") PostSort sortField,
                                                    @RequestParam("id") Long id) {
        return taskService.getTasksByAuthorOrExecutor(id, PageRequest.of(offset, limit, sortField.getSortValue()));
    }

    @Operation(summary = "Изменить статус задачи",
            description = "Обновляет статус задачи по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Статус успешно обновлён",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class)))
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long taskId, @Valid @RequestBody TaskStatusUpdateDto statusUpdateDto) {
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Status success changed!").getMessage())
                .body(taskService.updateTaskStatus(taskId, statusUpdateDto.getStatus()));
    }

    @Operation(summary = "Добавить комментарий к задаче",
            description = "Добавляет комментарий к задаче по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommentDto.class)))
    @PostMapping("/{taskId}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long taskId, @Valid @RequestBody CommentDto commentDto) {
        taskService.verifyTaskExecutor(taskId, userService.getAuthenticatedUser().getId());
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Comment success added!").getMessage())
                .body(commentService.addComment(taskId, commentDto.getText()));
    }
}
