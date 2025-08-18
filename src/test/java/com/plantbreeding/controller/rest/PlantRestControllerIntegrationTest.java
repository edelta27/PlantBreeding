package com.plantbreeding.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.repository.PlantRepository;
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

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;
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

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Test
    void shouldConnectToH2Database() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertThat(conn.isValid(1)).isTrue();
        }
    }
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

        int newHeight = 45;

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.patch("/plants/{id}", 1)
                        .param("healthStatus", "SICK")
                        .param("height", String.valueOf(newHeight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Plant updated successfully with id: " + 1));

        Optional<Plant> updatedPlant = plantRepository.findById(1L);
        assertThat(updatedPlant).isPresent();
        assertThat(updatedPlant.get().getHealthStatus()).isEqualTo(HealthStatus.SICK);
        assertThat(updatedPlant.get().getHeight()).isEqualTo(newHeight);
    }

    @Test
    void shouldDeletePlantById() throws Exception {
        // given

        assertThat(plantRepository.findById(1L)).isPresent();

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/plants/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("You deleted plant with id: " + 1));

        assertThat(plantRepository.findById(1L)).isNotPresent();
    }

}
