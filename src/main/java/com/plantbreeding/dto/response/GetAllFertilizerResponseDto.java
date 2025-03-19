package com.plantbreeding.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.dto.request.FertilizerDto;

import java.util.List;

public record GetAllFertilizerResponseDto(@JsonProperty("fertilizers") List<FertilizerDto> fertilizersDto) {

}
