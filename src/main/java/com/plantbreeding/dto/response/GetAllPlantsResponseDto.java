package com.plantbreeding.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.dto.request.PlantDto;

import java.util.List;

public record GetAllPlantsResponseDto(@JsonProperty("plants") List<PlantDto> plants) {

}
