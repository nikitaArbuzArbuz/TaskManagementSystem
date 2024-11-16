package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

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
    private String title;
    private String description;
    private Task.Status status;
    private Task.Priority priority;
    private Long authorId;
    private Long executorId;
    private List<CommentDto> comment;
}
