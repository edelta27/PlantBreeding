package com.plantbreeding.service.impl;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import com.plantbreeding.exception.PlantNotFoundException;
import com.plantbreeding.mapper.PlantMapper;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.service.PlantService;
import com.plantbreeding.specification.PlantSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public void addPlant(PlantDto plantDto) {
        log.info("save plant: ");
        Plant plant = plantMapper.toEntity(plantDto);
        plantRepository.save(plant);
    }

    /**
     * Retrieves a list of plants based on provided filtering criteria.
     *
     * @param isAnnual   optional plant is annual filter
     * @param type       optional plant type filter
     * @param limit      optional limit plant filter
     * @return a list of plants matching the specified filters
     */
    @Override
    public List<PlantDto> findFilteredPlants(Boolean isAnnual, PlantType type, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        Specification<Plant> spec = Specification
                .where(PlantSpecifications.hasIsAnnual(isAnnual))
                .and(PlantSpecifications.hasType(type));
        List<Plant> plants = plantRepository.findAll(spec, pageable).getContent();
        return plantMapper.toDtoList(plants);
    }

    /**
     * Retrieves a plant by its unique identifier.
     *
     * @param id the ID of the plant
     * @return the plant DTO if found
     * @throws PlantNotFoundException if the plant is not found
     */
    @Override
    public PlantDto getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + id + " not found"));
        return plantMapper.toDto(plant);
    }

    /**
     * Retrieves a plant along with all its associated tasks.
     *
     * @param plantId the ID of the plant
     * @return the plant DTO including its tasks
     * @throws PlantNotFoundException if the plant is not found
     */
    @Override
    public PlantWithTasksDto getPlantWithTasks(Long plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + plantId + " not found"));

        return plantMapper.toPlantWithTasksDto(plant);
    }

    /**
     * Updates the health status and height of an existing plant.
     *
     * @param id         the ID of the plant to update
     * @param newHealthStatus     the new health status
     * @param newHeight     the new height value
     * @return the saved plant entity
     * @throws PlantNotFoundException if the plant is not found
     */
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

    /**
     * Deletes a plant from the database.
     *
     * @param id the ID of the plant to delete
     * @throws PlantNotFoundException if the plant is not found
     */
    @Override
    @Transactional
    public void deletePlant(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id" + id + " not found"));
        plantRepository.deleteById(id);
    }
}
