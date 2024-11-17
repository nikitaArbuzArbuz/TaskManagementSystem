package ru.effective.mobile.java.taskmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.TaskRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.TaskMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.impl.TaskServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L)
                .setTitle("Test Task")
                .setDescription("Test Task")
                .setStatus(Task.Status.PENDING)
                .setPriority(Task.Priority.HIGH);

        taskDto = new TaskDto();
        taskDto.setTitle("Test Task")
                .setDescription("Test Task")
                .setStatus(Task.Status.PENDING)
                .setPriority(Task.Priority.HIGH);
    }

    @Test
    void shouldCreateTask() {
        TaskDto expectedTaskDto = new TaskDto();
        expectedTaskDto.setTitle("Test Task");

        when(taskMapper.map(taskDto)).thenReturn(task);
        when(taskRepository.saveAndFlush(task)).thenReturn(task);
        when(taskMapper.map(task)).thenReturn(expectedTaskDto);

        TaskDto result = taskService.createTask(taskDto);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).saveAndFlush(any(Task.class));
    }

    @Test
    void shouldGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        when(taskMapper.map(any(Task.class))).thenReturn(taskDto);

        TaskDto result = taskService.getTaskById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findById(1L);
    }
}

