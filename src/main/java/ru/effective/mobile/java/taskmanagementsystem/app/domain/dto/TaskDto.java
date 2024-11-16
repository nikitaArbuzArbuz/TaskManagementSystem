package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto implements Serializable {
    @NotBlank(message = "Please provide a title")
    @Size(min = 3, message = "Title must be min 3 characters")
    private String title;
    @Size(max = 200, message = "Description must be not long 200 characters")
    private String description;
    @NotBlank(message = "Please provide a status")
    private Task.Status status;
    @NotBlank(message = "Please provide a priority")
    private Task.Priority priority;
    private Long authorId;
    private Long executorId;
    private List<CommentDto> comment;
}
