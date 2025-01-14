package com.plantbreeding.infrastructure.dto.request;

import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.domain.entity.Task;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CreatePlantRequestDto(String name,
                                    String type,
                                    LocalDate plantingDate,
                                    String healthStatus,
                                    Boolean isAnnual,
                                    String description,
                                    Integer height,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt
) {
}
//this.name = name;
//        this.type = type;
//        this.plantingDate = plantingDate;
//        this.healthStatus = healthStatus;
//        this.isAnnual = isAnnual;
//        this.description = description;
//        this.height = height;
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();