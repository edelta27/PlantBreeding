package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import com.plantbreeding.infrastructure.dto.response.PlantWithTasksDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlantMapper {

    PlantDto toDto(Plant plant);
    List<PlantDto> toDtoList(List<Plant> plants);

    Plant toEntity(PlantDto plantDto);

    List<Plant> toEntityList(List<PlantDto> plantDtos);

    @Mapping(target = "tasks", ignore = true)
    PlantWithTasksDto toPlantWithTasksDto(Plant plant, List<TaskDto> tasks);

}

