package com.plantbreeding.service.impl;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import com.plantbreeding.exception.PlantNotFoundException;
import com.plantbreeding.mapper.PlantMapper;
import com.plantbreeding.mapper.TaskMapper;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.repository.TaskRepository;
import com.plantbreeding.service.PlantService;
import com.plantbreeding.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    @Override
    public List<Plant> findAll() {
        log.info("retrieving all plants: ");
        return plantRepository.findAll();
    }
    @Override
    @Transactional
    public void addPlant(PlantDto plantDto) {
        log.info("save plant: ");
        Plant plant = plantMapper.toEntity(plantDto);
        plantRepository.save(plant);
    }
    @Override
    public List<PlantDto> findFilteredPlants(Boolean isAnnual, PlantType type, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Plant> plants = plantRepository.findFilteredPlants(isAnnual, type, pageable);
        List<PlantDto> plantDtos = plantMapper.toDtoList(plants);
        return plantDtos;
    }
    @Override
    public PlantDto getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + id + " not found"));
        return plantMapper.toDto(plant);
    }
    @Override
    public PlantWithTasksDto getPlantWithTasks(Long plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + plantId + " not found"));

        return plantMapper.toPlantWithTasksDto(plant, taskMapper.toDtoList(plant.getTasks()));
    }
    @Override
    @Transactional
    public void updatePlantHealthAndHeight(Long id, HealthStatus newHealthStatus, Integer newHeight) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + id + " not found"));

        if (newHealthStatus != null) {
            plant.setHealthStatus(newHealthStatus);
        }
        if (newHeight != null) {
            plant.setHeight(newHeight);
        }

        plantRepository.save(plant);
    }
    @Override
    @Transactional
    public void deletePlant(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id" + id + " not found"));
        taskRepository.deleteByPlantId(id);
        plantRepository.deleteById(id);
    }
}
