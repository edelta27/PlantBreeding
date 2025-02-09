package com.plantbreeding.domain.service;

import com.plantbreeding.dao.PlantRepository;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enumeration.HealthStatus;
import com.plantbreeding.domain.enumeration.PlantType;
import com.plantbreeding.domain.errors.PlantNotFoundException;
import com.plantbreeding.infrastructure.dto.request.CreatePlantRequestDto;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import com.plantbreeding.infrastructure.mapper.PlantMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;
    @Autowired
    PlantService(PlantRepository plantRepository, PlantMapper plantMapper){
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
    }

    public List<Plant> findAll() {
        log.info("retrieving all plants: ");
        return plantRepository.findAll();
    }

    public void addPlant(CreatePlantRequestDto plantDto) {
        log.info("save plant: ");
        Plant plant = new Plant(
                plantDto.name(),
                plantDto.type(),
                plantDto.plantingDate(),
                plantDto.healthStatus(),
                plantDto.isAnnual(),
                plantDto.description(),
                plantDto.height()
        );
        plantRepository.save(plant);
    }

    public List<Plant> findFilteredPlants(Boolean isAnnual, PlantType type, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return plantRepository.findFilteredPlants(isAnnual, type, pageable);
    }

    public PlantDto getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + id + " not found"));
        return plantMapper.toDto(plant);
    }

    @Transactional
    public void updatePlantHealthAndHeight(Long id, HealthStatus newHealthStatus, int newHeight) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + id + " not found"));

        plant.setHealthStatus(newHealthStatus);
        plant.setHeight(newHeight);
    }
}
