package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.repository.PlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

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
    private Environment environment;

    @Test
    void printActiveProfile() {
        System.out.println(">>> Active profile: " + Arrays.toString(environment.getActiveProfiles()));
    }
    @BeforeEach
    void checkProfile() {
        System.out.println("Aktywny profil: " + System.getProperty("spring.profiles.active"));
    }
    @BeforeEach
    void setUp() {
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
    }

    @Test
    void shouldReturnAllPlants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/plants")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Tulip"))
                .andExpect(jsonPath("$[0].plantType").value("FLOWER"))
                .andExpect(jsonPath("$[0].isAnnual").value(true));
    }
}
