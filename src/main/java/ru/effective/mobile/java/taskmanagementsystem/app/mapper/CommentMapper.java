package ru.effective.mobile.java.taskmanagementsystem.app.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.CommentDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Comment;
import ru.effective.mobile.java.taskmanagementsystem.util.UserMapperHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
@Slf4j
public abstract class CommentMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "text", source = "text")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    public abstract CommentDto map(Comment comment);

    @IterableMapping(elementTargetType = CommentDto.class)
    public abstract List<CommentDto> map(List<Comment> comments);
}
