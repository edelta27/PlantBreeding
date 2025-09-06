package com.plantbreeding.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.repository.PlantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
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
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllPlants() throws Exception {
        // given
        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/plants")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].name").value("Tulip"))
                .andExpect(jsonPath("$.content[0].plantType").value("FLOWER"))
                .andExpect(jsonPath("$.content[0].isAnnual").value(true));
    }

    @Test
    void shouldReturnPlantById() throws Exception {
        // given
        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/plants/{id}", 1)
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
        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/plants/{id}/tasks", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tulip"))
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
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.name").value("Rose"));

    }

    @Test
    void shouldUpdatePlantSickAndHeight() throws Exception {
        // given
        int newHeight = 45;

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.patch("/plants/{id}", 1)
                        .param("healthStatus", "SICK")
                        .param("height", String.valueOf(newHeight)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.healthStatus").value("SICK"))
                .andExpect(jsonPath("$.height").value(45));


        Optional<Plant> updatedPlant = plantRepository.findById(1L);
        assertThat(updatedPlant).isPresent();
        assertThat(updatedPlant.get().getHealthStatus()).isEqualTo(HealthStatus.SICK);
        assertThat(updatedPlant.get().getHeight()).isEqualTo(newHeight);
    }

    @Test
    void shouldDeletePlantById() throws Exception {
        // given
        assertThat(plantRepository.findById(3L)).isPresent();

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/plants/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("You deleted plant with id: " + 3));

        assertThat(plantRepository.findById(3L)).isNotPresent();
    }

}
