package ru.effective.mobile.java.taskmanagementsystem.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.service.CommentService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;
import ru.effective.mobile.java.taskmanagementsystem.util.responses.MessageResponse;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Управление задачами для администратора")
public class AdminController {
    private final TaskService taskService;
    private final CommentService commentService;

    @Operation(summary = "Получить задачу по ID",
            description = "Возвращает задачу по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Задача найдена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class)))
    @GetMapping
    public TaskDto getTask(@RequestParam Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Создать задачу",
            description = "Создаёт новую задачу")
    @ApiResponse(responseCode = "201", description = "Задача успешно создана",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class)))
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Task created!").getMessage())
                .body(taskService.createTask(taskDto));
    }

    @Operation(summary = "Редактировать задачу",
            description = "Редактирует существующую задачу по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Задача успешно обновлена",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class)))
    @PatchMapping("/{id}/edit")
    public ResponseEntity<TaskDto> editTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task executor updated!").getMessage())
                .body(taskService.editTask(id, taskDto));
    }

    @Operation(summary = "Добавить комментарий к задаче",
            description = "Добавляет комментарий к задаче по её идентификатору")
    @ApiResponse(responseCode = "201", description = "Комментарий успешно добавлен",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommentDto.class)))
    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Server", new MessageResponse("Comment success added!").getMessage())
                .body(commentService.addComment(id, commentDto.getText()));
    }

    @Operation(summary = "Удалить задачу",
            description = "Удаляет задачу по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Задача успешно удалена")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok()
                .header("Server", new MessageResponse("Task success deleted!").getMessage())
                .build();
    }
}
