package com.plantbreeding.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantbreeding.domain.entity.Fertilizer;

import java.util.List;

public record GetAllFertilizerResponseDto(@JsonProperty("fertilizers") List<Fertilizer> fertilizers) {

}
