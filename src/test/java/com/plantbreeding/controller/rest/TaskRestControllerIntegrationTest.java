package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.domain.enums.TaskType;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private TaskRepository taskRepository;


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
        plant.setDescription("Test description plant");

        plantRepository.save(plant);

        Task task = new Task();
        task.setPlant(plant);
        task.setTaskDate(LocalDate.of(2025, 3, 1));
        task.setTaskType(TaskType.WATERING);
        task.setStatus(TaskStatus.OVERDUE);
        task.setNotes("Test description task");

        taskRepository.save(task);
    }

    @Test
    void shouldReturnTaskDaily() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/daily?date=2025-03-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taskType").value("WATERING"))
                .andExpect(jsonPath("$[0].notes").value("Test description task"))
                .andExpect(jsonPath("$[0].taskDate").value("2025-03-01"))
                .andExpect(jsonPath("$[0].status").value("OVERDUE"));
    }
}
