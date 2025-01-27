package com.plantbreeding.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.domain.entity.Task;

import java.util.List;

public record GetAllTasksResponseDto(@JsonProperty("tasks") List<Task> tasks) {
}
