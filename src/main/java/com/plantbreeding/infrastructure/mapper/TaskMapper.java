package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto TaskDto);
}

