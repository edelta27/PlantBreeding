package com.plantbreeding.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.domain.entity.Plant;

import java.util.Map;

public record GetAllPlantsResponseDto(@JsonProperty("plants") Map<Long, Plant> plants) {

}
