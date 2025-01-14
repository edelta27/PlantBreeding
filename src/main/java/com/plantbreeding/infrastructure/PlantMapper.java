package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.infrastructure.dto.request.CreatePlantRequestDto;

//@Mapper(componentModel = "spring")
public interface PlantMapper {
    CreatePlantRequestDto toDto(Plant plant);
    Plant toEntity(CreatePlantRequestDto plantDto);
}

