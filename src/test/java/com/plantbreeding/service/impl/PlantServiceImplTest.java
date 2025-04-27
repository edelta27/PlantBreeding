package com.plantbreeding.service.impl;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.repository.TaskRepository;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import com.plantbreeding.mapper.PlantMapper;
import com.plantbreeding.service.TaskService;
import com.plantbreeding.exception.PlantNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PlantServiceImplTest {
    @Mock
    private PlantRepository plantRepository;
    @Mock
    private PlantMapper plantMapper;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskService taskService;

    @InjectMocks
    private PlantServiceImpl plantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllPlants() {
        // given
        Plant plant = new Plant();
        given(plantRepository.findAll()).willReturn(List.of(plant));

        // when
        List<Plant> result = plantService.findAll();

        // then
        assertThat(result).hasSize(1);
        verify(plantRepository).findAll();
    }

    @Test
    void shouldAddPlant() {
        // given
        PlantDto plantDto = new PlantDto(1L, "Rose", PlantType.FLOWER, LocalDate.now(), HealthStatus.HEALTHY, true, "small flower", 20);
        Plant plant = new Plant();
        given(plantMapper.toEntity(plantDto)).willReturn(plant);

        // when
        plantService.addPlant(plantDto);

        // then
        verify(plantMapper).toEntity(plantDto);
        verify(plantRepository).save(plant);
    }

    @Test
    void shouldFindFilteredPlants() {
        // given
        Boolean isAnnual = true;
        PlantType type = PlantType.FLOWER;
        int limit = 5;
        Pageable pageable = PageRequest.of(0, limit);

        Plant plant = new Plant();
        PlantDto plantDto = new PlantDto(1L, "Tulip", PlantType.FLOWER, LocalDate.now(), HealthStatus.HEALTHY, true, "yellow flower", 15);

        given(plantRepository.findFilteredPlants(isAnnual, type, pageable)).willReturn(List.of(plant));
        given(plantMapper.toDtoList(List.of(plant))).willReturn(List.of(plantDto));

        // when
        List<PlantDto> result = plantService.findFilteredPlants(isAnnual, type, limit);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Tulip");
    }

    @Test
    void shouldGetPlantById() {
        // given
        Long plantId = 1L;
        Plant plant = new Plant();
        PlantDto plantDto = new PlantDto(plantId, "Orchid", PlantType.FLOWER, LocalDate.now(), HealthStatus.HEALTHY, false, "orchid flower", 30);

        given(plantRepository.findById(plantId)).willReturn(Optional.of(plant));
        given(plantMapper.toDto(plant)).willReturn(plantDto);

        // when
        PlantDto result = plantService.getPlantById(plantId);

        // then
        assertThat(result.id()).isEqualTo(plantId);
        assertThat(result.name()).isEqualTo("Orchid");
    }

    @Test
    void shouldThrowExceptionWhenPlantNotFound() {
        // given
        Long plantId = 1L;
        given(plantRepository.findById(plantId)).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> plantService.getPlantById(plantId))
                .isInstanceOf(PlantNotFoundException.class)
                .hasMessageContaining("Plant with id " + plantId + " not found");
    }

    @Test
    void shouldGetPlantWithTasks() {
        // given
        Long plantId = 1L;
        Plant plant = new Plant();
        PlantWithTasksDto plantWithTasksDto = new PlantWithTasksDto(plantId, "Tulip", PlantType.FLOWER, HealthStatus.HEALTHY,20, List.of());

        given(plantRepository.findById(plantId)).willReturn(Optional.of(plant));
        given(taskService.findTasksByPlantId(plantId)).willReturn(List.of());
        given(plantMapper.toPlantWithTasksDto(plant, List.of())).willReturn(plantWithTasksDto);

        // when
        PlantWithTasksDto result = plantService.getPlantWithTasks(plantId);

        // then
        assertThat(result.id()).isEqualTo(plantId);
        verify(taskService).findTasksByPlantId(plantId);
    }

    @Test
    void shouldUpdatePlantHealthAndHeight() {
        // given
        Long plantId = 1L;
        HealthStatus newStatus = HealthStatus.SICK;
        Integer newHeight = 40;
        Plant plant = new Plant();
        plant.setHealthStatus(HealthStatus.HEALTHY);
        plant.setHeight(20);

        given(plantRepository.findById(plantId)).willReturn(Optional.of(plant));

        // when
        plantService.updatePlantHealthAndHeight(plantId, newStatus, newHeight);

        // then
        assertThat(plant.getHealthStatus()).isEqualTo(HealthStatus.SICK);
        assertThat(plant.getHeight()).isEqualTo(40);
        verify(plantRepository).save(plant);
    }

    @Test
    void shouldDeletePlant() {
        // given
        Long plantId = 1L;
        Plant plant = new Plant();
        given(plantRepository.findById(plantId)).willReturn(Optional.of(plant));

        // when
        plantService.deletePlant(plantId);

        // then
        verify(taskRepository).deleteByPlantId(plantId);
        verify(plantRepository).deleteById(plantId);
    }
}