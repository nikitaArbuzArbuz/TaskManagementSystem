package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import lombok.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

import java.io.Serializable;

/**
 * DTO for {@link ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminTaskDto implements Serializable {
    private String title;
    private String description;
    private Task.Status status;
    private Task.Priority priority;
    private Long authorId;
    private Long executorId;
}