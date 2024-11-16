package ru.effective.mobile.java.taskmanagementsystem.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.PostSort;
import ru.effective.mobile.java.taskmanagementsystem.app.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasksByAuthorOrExecutor(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                    @RequestParam(value = "sort", defaultValue = "TITLE_ASC") PostSort sortField,
                                                    @RequestParam("id") Long id) {
        return taskService.getTasksByAuthorOrExecutor(id, PageRequest.of(offset, limit, sortField.getSortValue()));
    }
}
