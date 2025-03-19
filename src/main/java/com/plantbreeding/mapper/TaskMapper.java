package com.plantbreeding.mapper;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.dto.request.TaskDto;
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

