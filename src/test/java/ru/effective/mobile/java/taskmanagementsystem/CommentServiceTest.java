package ru.effective.mobile.java.taskmanagementsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.CommentRepository;
import ru.effective.mobile.java.taskmanagementsystem.adapter.repository.TaskRepository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.User;
import ru.effective.mobile.java.taskmanagementsystem.app.mapper.CommentMapper;
import ru.effective.mobile.java.taskmanagementsystem.app.service.UserService;
import ru.effective.mobile.java.taskmanagementsystem.app.service.impl.CommentServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        user = new User();
        user.setLogin("testuser");
    }

    @Test
    void shouldAddComment() {
        String commentText = "Test comment";
        Comment comment = new Comment();
        comment.setText(commentText);

        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(commentRepository.saveAndFlush(any(Comment.class))).thenReturn(comment);
        when(commentMapper.map(any(Comment.class))).thenReturn(new CommentDto(commentText, 0L, 0L));

        CommentDto result = commentService.addComment(1L, commentText);

        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo(commentText);
        verify(commentRepository, times(1)).saveAndFlush(any(Comment.class));
    }
}

