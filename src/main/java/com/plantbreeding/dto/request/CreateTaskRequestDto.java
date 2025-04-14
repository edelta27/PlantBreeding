package com.plantbreeding.dto.request;

import com.plantbreeding.domain.enums.Recurrence;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.domain.enums.TaskType;
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
