package com.plantbreeding.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.infrastructure.dto.request.PlantDto;

import java.util.List;

public record GetAllPlantsResponseDto(@JsonProperty("plants") List<PlantDto> plants) {

}
