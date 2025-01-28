package com.plantbreeding.infrastructure.dto.request;

import com.plantbreeding.domain.enumeration.HealthStatus;
import com.plantbreeding.domain.enumeration.PlantType;

import java.time.LocalDate;

public record PlantDto(Long id,
                       String name,
                       PlantType type,
                       LocalDate plantingDate,
                       HealthStatus healthStatus,
                       Boolean isAnnual,
                       String description,
                       Integer height
) {
}
