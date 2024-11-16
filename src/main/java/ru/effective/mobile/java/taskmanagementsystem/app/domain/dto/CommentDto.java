package ru.effective.mobile.java.taskmanagementsystem.app.domain.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto implements Serializable {
    private String text;
    private Long authorId;
    private Long taskId;
}