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
    String title;
    String description;
    Task.Status status;
    Task.Priority priority;
    Long authorId;
    Long executorId;
}