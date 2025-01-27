package com.plantbreeding.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.domain.entity.Plant;

import java.util.List;

public record GetAnnualAndTypePlantsResponseDto(@JsonProperty("plants") List<Plant> plants) {
}
