package com.plantbreeding.mapper;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    @Mapping(target = "plantType", source = "type")
    PlantDto toDto(Plant plant);
    List<PlantDto> toDtoList(List<Plant> plants);
    @Mapping(target = "type", source = "plantType")
    Plant toEntity(PlantDto plantDto);

    List<Plant> toEntityList(List<PlantDto> plantDtos);

    @Mapping(target = "tasks", ignore = true)
    PlantWithTasksDto toPlantWithTasksDto(Plant plant, List<TaskDto> tasks);


}

