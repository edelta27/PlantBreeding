package com.plantbreeding.service.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.domain.enums.TaskType;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;
import com.plantbreeding.mapper.PlantMapper;
import com.plantbreeding.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @InjectMocks
    private PlantServiceImpl plantService;

    @Test
    void shouldFindAllPlants() {
        // given
        Plant plant = new Plant("Tomato",
                                PlantType.VEGETABLE,
                                LocalDate.parse("2025-02-25"),
                                HealthStatus.HEALTHY,
                                true,
                                "A juicy tomato plant.",
                                50);
        List<Plant> plantList = List.of(plant);

        when(plantRepository.findAll()).thenReturn(plantList);

        // when
        List<Plant> result = plantService.findAll();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tomato", result.get(0).getName());
        verify(plantRepository, times(1)).findAll();
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

        Page<Plant> page = new PageImpl<>(List.of(plant));
        given(plantRepository.findFilteredPlants(isAnnual, type, pageable)).willReturn(page);
        given(plantMapper.toDto(plant)).willReturn(plantDto);

        // when
        Page<PlantDto> result = plantService.findFilteredPlants(isAnnual, type, pageable);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Tulip");
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
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Plant with id " + plantId + " not found");
    }

    @Test
    void shouldGetPlantWithTasks() {
        // given
        LocalDate localDate = LocalDate.of(2025,3,1) ;
        Long plantId = 1L;
        Plant plant = new Plant("Tulip", PlantType.FLOWER, LocalDate.of(2024,3,1),HealthStatus.HEALTHY, true, "Test description",25);
        plant.setId(plantId);
        Task task = new Task(1L, TaskType.WATERING, "Water me", localDate, TaskStatus.OVERDUE,1L );
        task.setPlant(plant);
        plant.setTasks(List.of(task));

        PlantWithTasksDto plantWithTasksDto = new PlantWithTasksDto(plantId, "Tulip", PlantType.FLOWER, HealthStatus.HEALTHY,20, List.of(new TaskDto(1L, TaskType.WATERING, "Water me", localDate, TaskStatus.OVERDUE,1L )));

        given(plantRepository.findByIdWithTasks(plantId)).willReturn(Optional.of(plant));
        given(plantMapper.toPlantWithTasksDto(plant)).willReturn(plantWithTasksDto);

        // when
        PlantWithTasksDto result = plantService.getPlantWithTasks(plantId);

        // then
        assertThat(result.id()).isEqualTo(plantId);
        assertThat(result.name()).isEqualTo("Tulip");
        assertThat(result.type()).isEqualTo(PlantType.FLOWER);
        assertThat(result.tasks().get(0).notes()).isEqualTo("Water me");
        assertThat(result.tasks()).hasSize(1);
        assertThat(result.tasks().get(0).taskType()).isEqualTo(TaskType.WATERING);
        assertThat(result.tasks().get(0).taskDate()).isEqualTo(localDate);
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
        given(plantRepository.existsById(plantId)).willReturn(true);

        // when
        plantService.deletePlant(plantId);

        // then
        verify(plantRepository).deleteById(plantId);
    }

    @Test
    void shouldThrowExceptionWhenPlantDoesNotExist() {
        // given
        Long plantId = 1L;
        given(plantRepository.existsById(plantId)).willReturn(false);

        // when + then
        assertThrows(ResourceNotFoundException.class,
                () -> plantService.deletePlant(plantId));
        verify(plantRepository, never()).deleteById(anyLong());
    }
}