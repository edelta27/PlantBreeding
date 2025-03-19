package com.plantbreeding.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.enumeration.TaskType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record TaskDto(
        Long id,
        @NotNull
        TaskType taskType,
        String notes,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate taskDate,
        @NotNull
        TaskStatus status,
        Long plantId
) { }