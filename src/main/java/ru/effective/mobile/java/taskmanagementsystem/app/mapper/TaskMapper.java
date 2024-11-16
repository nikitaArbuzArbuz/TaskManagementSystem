package ru.effective.mobile.java.taskmanagementsystem.app.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.util.UserMapperHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class, CommentMapper.class})
@Slf4j
public abstract class TaskMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapUserFromId")
    @Mapping(target = "executor", source = "executorId", qualifiedByName = "mapUserFromId")
    @Mapping(target = "comments", source = "comment")
    public abstract Task map(TaskDto taskDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "executorId", source = "executor.id")
    @Mapping(target = "comment", source = "comments")
    public abstract TaskDto map(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapUserFromId")
    @Mapping(target = "executor", source = "executorId", qualifiedByName = "mapUserFromId")
    public abstract void updateTaskFromDto(TaskDto taskDto, @MappingTarget Task task);

    @IterableMapping(elementTargetType = TaskDto.class)
    public abstract List<TaskDto> map(List<Task> taskList);
}

//package ru.effective.mobile.java.taskmanagementsystem.app.mapper;
//
//import lombok.extern.slf4j.Slf4j;
//import org.mapstruct.*;
//        import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.AdminTaskDto;
//import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.TaskDto;
//import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
//import ru.effective.mobile.java.taskmanagementsystem.util.UserMapperHelper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {CommentMapper.class})
//@Slf4j
//public abstract class TaskNewMapper {
//    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "title", source = "title")
//    @Mapping(target = "description", source = "description")
//    @Mapping(target = "status", source = "status")
//    @Mapping(target = "priority", source = "priority")
//    @Mapping(target = "authorId", source = "author.id")
//    @Mapping(target = "executorId", source = "executor.id")
//    @Mapping(target = "comment", source = "comments")
//    public abstract TaskDto map(Task task);
//
//    @IterableMapping(elementTargetType = TaskDto.class)
//    public abstract List<TaskDto> map(List<Task> taskList);
//}
