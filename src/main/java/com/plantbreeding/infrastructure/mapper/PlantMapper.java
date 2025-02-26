package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PlantMapper {

    PlantDto toDto(Plant plant);
    List<PlantDto> toDtoList(List<Plant> plants);

    Plant toEntity(PlantDto plantDto);

    List<Plant> toEntityList(List<PlantDto> plantDtos);

}

