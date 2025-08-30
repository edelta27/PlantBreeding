package com.plantbreeding.service;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import java.util.List;

public interface PlantService {
    List<Plant> findAll();
    PlantDto addPlant(PlantDto plantDto);
    List<PlantDto> findFilteredPlants(Boolean isAnnual, PlantType type, int limit);
    PlantDto getPlantById(Long id);
    PlantWithTasksDto getPlantWithTasks(Long plantId);
    void updatePlantHealthAndHeight(Long id, HealthStatus newHealthStatus, Integer newHeight);
    void deletePlant(Long id);
}
