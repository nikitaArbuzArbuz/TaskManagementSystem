package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

@Data
public class TaskStatusUpdateDto {
    @NotBlank(message = "Please provide a status")
    private Task.Status status;
}
