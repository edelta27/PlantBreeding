package com.plantbreeding.service.impl;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import com.plantbreeding.exception.ResourceNotFoundException;
import com.plantbreeding.mapper.PlantMapper;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.service.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing plants.
 * Handles business logic for CRUD operations on plants.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;

    /**
     * Retrieves all plants from the database.
     *
     * @return a list of plants
     */
    @Override
    public List<Plant> findAll() {
        log.info("retrieving all plants: ");
        return plantRepository.findAll();
    }

    /**
     * Adds a new plant to the database.
     *
     * @param plantDto the plant entity to add
     * @return the saved plant entity
     */
    @Override
    @Transactional
    public PlantDto addPlant(PlantDto plantDto) {
        log.info("save plant: ");
        Plant plant = plantMapper.toEntity(plantDto);
        Plant newPlant = plantRepository.save(plant);
        return plantMapper.toDto(newPlant);
    }

    /**
     * Retrieves a list of plants based on provided filtering criteria.
     *
     * @param isAnnual   optional plant is annual filter
     * @param type       optional plant type filter
     * @param pageable   optional limit plant filter
     * @return a list of plants matching the specified filters
     */
    @Override
    public Page<PlantDto> findFilteredPlants(Boolean isAnnual, PlantType type, Pageable pageable) {
        Page<Plant> plants = plantRepository.findFilteredPlants(isAnnual, type, pageable);
        return plants.map(plantMapper::toDto);
    }

    /**
     * Retrieves a plant by its unique identifier.
     *
     * @param id the ID of the plant
     * @return the plant DTO if found
     * @throws ResourceNotFoundException if the plant is not found
     */
    @Override
    public PlantDto getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant with id " + id + " not found"));
        return plantMapper.toDto(plant);
    }


    /**
     * Retrieves a plant along with all its associated tasks.
     *
     * @param plantId the ID of the plant
     * @return the plant DTO including its tasks
     * @throws ResourceNotFoundException if the plant is not found
     */
    @Override
    public PlantWithTasksDto getPlantWithTasks(Long plantId) {
        Plant plant = plantRepository.findByIdWithTasks(plantId)
                .orElseThrow(() -> new ResourceNotFoundException("Plant with id " + plantId + " not found"));

        return plantMapper.toPlantWithTasksDto(plant);
    }

    /**
     * Updates the health status and height of an existing plant.
     *
     * @param id         the ID of the plant to update
     * @param newHealthStatus     the new health status
     * @param newHeight     the new height value
     * @return the saved plant entity
     * @throws ResourceNotFoundException if the plant is not found
     */
    @Override
    @Transactional
    public void updatePlantHealthAndHeight(Long id, HealthStatus newHealthStatus, Integer newHeight) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant with id " + id + " not found"));

        if (newHealthStatus != null) {
            plant.setHealthStatus(newHealthStatus);
        }
        if (newHeight != null) {
            plant.setHeight(newHeight);
        }

        plantRepository.save(plant);
    }

    /**
     * Deletes a plant from the database.
     *
     * @param id the ID of the plant to delete
     * @throws ResourceNotFoundException if the plant is not found
     */
    @Override
    @Transactional
    public void deletePlant(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant with id" + id + " not found"));
        plantRepository.deleteById(id);
    }
}
