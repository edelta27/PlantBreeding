package com.plantbreeding.infrastructure.dto.request;

import com.plantbreeding.domain.enumeration.Recurrence;
import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.enumeration.TaskType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTaskRequestDto(@NotNull Long plantId,
                                   @NotNull TaskType taskType,
                                   String notes,
                                   @NotNull TaskStatus taskStatus,
                                   @NotNull LocalDate startDate,
                                   @NotNull LocalDate endDate,
                                   @NotNull Recurrence recurrence
) {}
