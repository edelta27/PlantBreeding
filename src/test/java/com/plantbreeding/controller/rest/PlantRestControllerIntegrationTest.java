package com.plantbreeding.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.domain.enums.TaskType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PlantRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment environment;

    @Test
    void printActiveProfile() {
        System.out.println(">>> Active profile: " + Arrays.toString(environment.getActiveProfiles()));
    }
    @BeforeEach
    void checkProfile() {
        System.out.println("Aktywny profil: " + System.getProperty("spring.profiles.active"));
    }


    @Test
    void shouldReturnAllPlants() throws Exception {
        // given
        plantRepository.deleteAll();
        Plant plant = new Plant("Tulip", PlantType.FLOWER, LocalDate.of(2024, 3, 1), HealthStatus.HEALTHY, true, "Test", 25);
        plantRepository.save(plant);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/plants")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Tulip"))
                .andExpect(jsonPath("$[0].plantType").value("FLOWER"))
                .andExpect(jsonPath("$[0].isAnnual").value(true));
    }

    @Test
    void shouldReturnPlantById() throws Exception {
        // given
        Plant plant = new Plant("Tulip", PlantType.FLOWER, LocalDate.of(2024, 3, 1), HealthStatus.HEALTHY, true, "Test", 25);
        plantRepository.save(plant);
        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/plants/{id}", plant.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tulip"))
                .andExpect(jsonPath("$.plantType").value("FLOWER"))
                .andExpect(jsonPath("$.isAnnual").value(true));
    }

    @Test
    void shouldReturn404WhenPlantNotFound() throws Exception {
        Long nonExistingId = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get("/plants/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPlantWithTasks() throws Exception {
        // given
        plantRepository.deleteAll();
        Plant plant = new Plant();
        plant.setName("Tulip");
        plant.setType(PlantType.FLOWER);
        plant.setHealthStatus(HealthStatus.HEALTHY);
        plant.setIsAnnual(true);
        plant.setHeight(25);
        plant.setPlantingDate(LocalDate.of(2024, 3, 1));
        plant.setDescription("Test description");

        plantRepository.save(plant);

        Task task = new Task(null, TaskType.WATERING, "Water me", LocalDate.of(2025, 3, 1), TaskStatus.OVERDUE, plant.getId());
        task.setPlant(plant);
        taskRepository.save(task);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/plants/{id}/tasks", plant.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tulip"))
                .andExpect(jsonPath("$.tasks", hasSize(1)))
                .andExpect(jsonPath("$.tasks[0].taskType").value("WATERING"))
                .andExpect(jsonPath("$.tasks[0].notes").value("Water me"))
                .andExpect(jsonPath("$.tasks[0].status").value("OVERDUE"));
    }

    @Test
    void shouldAddNewPlantSuccessfully() throws Exception {
        // given
        PlantDto plantDto = new PlantDto(
                null,
                "Rose",
                PlantType.FLOWER,
                LocalDate.of(2024, 5, 1),
                HealthStatus.HEALTHY,
                true,
                "Beautiful red rose",
                30
        );

        String json = objectMapper.writeValueAsString(plantDto);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/plants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Plant added successfully"))
                .andExpect(jsonPath("$.status").value("OK"));

    }

    @Test
    void shouldUpdatePlantSickAndHeight() throws Exception {
        // given
        Plant plant = new Plant("Tulip", PlantType.FLOWER, LocalDate.of(2024, 3, 1), HealthStatus.HEALTHY, true, "Test", 25);
        plantRepository.save(plant);
        HealthStatus newHealthStatus = HealthStatus.SICK;
        int newHeight = 45;

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.patch("/plants/{id}", plant.getId())
                        .param("healthStatus", newHealthStatus.name())
                        .param("height", String.valueOf(newHeight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Plant updated successfully with id: " + plant.getId()));

        Optional<Plant> updatedPlant = plantRepository.findById(plant.getId());
        assertThat(updatedPlant).isPresent();
        assertThat(updatedPlant.get().getHealthStatus()).isEqualTo(newHealthStatus);
        assertThat(updatedPlant.get().getHeight()).isEqualTo(newHeight);
    }

    @Test
    void shouldDeletePlantById() throws Exception {
        // given
        Plant plant = new Plant("Tulip", PlantType.FLOWER, LocalDate.of(2024, 3, 1), HealthStatus.HEALTHY, true, "Test", 25);
        plantRepository.save(plant);
        assertThat(plantRepository.findById(plant.getId())).isPresent();

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/plants/{id}", plant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("You deleted plant with id: " + plant.getId()));

        assertThat(plantRepository.findById(plant.getId())).isNotPresent();
    }

}
