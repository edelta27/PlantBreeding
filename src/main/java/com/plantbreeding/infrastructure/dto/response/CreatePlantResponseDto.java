package com.plantbreeding.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.domain.entity.Plant;

public record CreatePlantResponseDto(@JsonProperty("plant") Plant plant) {
}
