package com.plantbreeding.infrastructure.dto.request;

import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.enumeration.TaskType;

import java.time.LocalDate;

public record TaskDto(Long id,
                      TaskType taskType,
                      String notes,
                      LocalDate taskDate,
                      TaskStatus status
) {
}