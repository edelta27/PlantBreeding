package com.plantbreeding.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.infrastructure.dto.request.FertilizerDto;

import java.util.List;

public record GetAllFertilizerResponseDto(@JsonProperty("fertilizers") List<FertilizerDto> fertilizersDto) {

}
