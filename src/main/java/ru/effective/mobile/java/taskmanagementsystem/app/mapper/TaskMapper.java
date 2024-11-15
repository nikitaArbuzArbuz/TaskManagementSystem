package ru.effective.mobile.java.taskmanagementsystem.app.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.dto.AdminTaskDto;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;
import ru.effective.mobile.java.taskmanagementsystem.util.UserMapperHelper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
@Slf4j
public abstract class TaskMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapUserFromId")
    @Mapping(target = "executor", source = "executorId", qualifiedByName = "mapUserFromId")
    public abstract Task map(AdminTaskDto taskDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "executorId", source = "executor.id")
    public abstract AdminTaskDto map(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapUserFromId")
    @Mapping(target = "executor", source = "executorId", qualifiedByName = "mapUserFromId")
    public abstract void updateTaskFromDto(AdminTaskDto adminTaskDto, @MappingTarget Task task);

    @IterableMapping(elementTargetType = Task.class)
    public abstract List<AdminTaskDto> map(List<Task> taskList);
}
