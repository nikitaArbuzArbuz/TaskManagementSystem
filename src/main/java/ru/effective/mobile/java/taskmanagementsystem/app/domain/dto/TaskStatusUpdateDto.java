package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import lombok.Data;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

@Data
public class TaskStatusUpdateDto {
    private Task.Status status;
}
