package com.plantbreeding.dto.response;

import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.TaskDto;
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
