package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    PlantDto toDto(Plant plant);
    Plant toEntity(PlantDto plantDto);
}

