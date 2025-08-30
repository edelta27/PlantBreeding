package com.plantbreeding.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantbreeding.domain.enums.Recurrence;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.domain.enums.TaskType;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
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
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTaskDaily() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/daily?date=2025-03-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taskType").value("WATERING"))
                .andExpect(jsonPath("$[0].notes").value("Water me"))
                .andExpect(jsonPath("$[0].taskDate").value("2025-03-01"))
                .andExpect(jsonPath("$[0].status").value("DONE"));
    }

    @Test
    void shouldAddTaskSuccessfully() throws Exception {
        // given
        CreateTaskRequestDto requestDto = new CreateTaskRequestDto(
                1L,
                TaskType.WATERING,
                "watering once a week",
                TaskStatus.SCHEDULED,
                LocalDate.of(2025, 7, 30),
                LocalDate.of(2025, 8, 5),
                Recurrence.WEEKLY
        );

        String json = objectMapper.writeValueAsString(requestDto);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].taskType").value("WATERING"));
    }

    @Test
    void shouldUpdateTaskStatusSuccessfully() throws Exception {
        // given
        Long taskId = 2L;
        String newStatus = String.valueOf(TaskStatus.DONE);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.patch("/tasks/{id}", taskId)
                        .param("taskStatus", newStatus)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Task updated successfully"))
                .andExpect(jsonPath("$.status").value("OK"));
    }

}
