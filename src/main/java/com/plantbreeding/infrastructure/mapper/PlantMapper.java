package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.infrastructure.dto.request.CreatePlantRequestDto;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import com.plantbreeding.infrastructure.dto.response.PlantWithTasksDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    @Mapping(target = "plantType", source = "type")
    @Mapping(target = "isAnnual", defaultValue = "false")
    PlantDto toDto(Plant plant);
    List<PlantDto> toDtoList(List<Plant> plants);

    Plant toEntity(PlantDto plantDto);

    List<Plant> toEntityList(List<PlantDto> plantDtos);

    @Mapping(target = "tasks", ignore = true)
    PlantWithTasksDto toPlantWithTasksDto(Plant plant, List<TaskDto> tasks);
    Plant toEntity(CreatePlantRequestDto plantDto);

}

