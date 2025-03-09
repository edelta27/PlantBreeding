package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "plantId", source = "plant.id")
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);

    List<TaskDto> toDtoList(List<Task> tasks);
    List<Task> toEntityList(List<TaskDto> taskDtos);
}

