package com.plantbreeding.infrastructure.dto.response;

import com.plantbreeding.domain.enumeration.HealthStatus;
import com.plantbreeding.domain.enumeration.PlantType;
import com.plantbreeding.domain.enumeration.TaskType;
import com.plantbreeding.infrastructure.dto.request.TaskDto;

import java.util.List;

public record PlantWithTasksDto(
        Long id,
        String name,
        PlantType type,
        HealthStatus healthStatus,
        int height,
        List<TaskDto> tasks
) {
}
