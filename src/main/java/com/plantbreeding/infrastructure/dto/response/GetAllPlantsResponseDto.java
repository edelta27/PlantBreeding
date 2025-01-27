package com.plantbreeding.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.domain.entity.Plant;

import java.util.List;
import java.util.Map;

public record GetAllPlantsResponseDto(@JsonProperty("plants") List<Plant> plants) {

}
