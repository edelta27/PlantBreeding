package com.plantbreeding.service;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlantService {
    List<Plant> findAll();
    PlantDto addPlant(PlantDto plantDto);
    Page<PlantDto> findFilteredPlants(Boolean isAnnual, PlantType type, Pageable pageable);
    PlantDto getPlantById(Long id);
    Plant getPlantEntityById(Long id);
    PlantWithTasksDto getPlantWithTasks(Long plantId);
    PlantDto updatePlantHealthAndHeight(Long id, HealthStatus newHealthStatus, Integer newHeight);
    void deletePlant(Long id);
}
