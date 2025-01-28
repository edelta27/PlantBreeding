package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.infrastructure.dto.request.CreatePlantRequestDto;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import com.plantbreeding.infrastructure.dto.response.CreatePlantResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    PlantDto toDto(Plant plant);

    Plant toEntity(PlantDto plantDto);

}

