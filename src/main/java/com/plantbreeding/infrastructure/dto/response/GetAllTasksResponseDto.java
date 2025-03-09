package com.plantbreeding.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.infrastructure.dto.request.TaskDto;

import java.util.List;

public record GetAllTasksResponseDto(@JsonProperty("tasks") List<TaskDto> taskDtos) {
}
