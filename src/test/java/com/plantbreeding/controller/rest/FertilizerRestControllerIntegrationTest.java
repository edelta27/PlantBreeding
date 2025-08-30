package com.plantbreeding.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plantbreeding.domain.enums.ApplicationMethod;
import com.plantbreeding.domain.enums.FertilizerType;
import com.plantbreeding.dto.request.FertilizerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FertilizerRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllFertilizers() throws Exception {
        // given

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/fertilizers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Fert"))
                .andExpect(jsonPath("$[0].type").value("ORGANIC"))
                .andExpect(jsonPath("$[0].applicationMethod").value("WATER_SOLUBLE"))
                .andExpect(jsonPath("$[0].usageRecommendations").value("Test"));
    }

    @Test
    void shouldAddNewFertilizerSuccessfully() throws Exception {
        // given
        FertilizerDto fertilizerDto = new FertilizerDto(
                null,
                "Target",
                FertilizerType.MINERAL,
                ApplicationMethod.WATER_SOLUBLE,
                "Use once a month, intended for vegetables."
        );

        String json = objectMapper.writeValueAsString(fertilizerDto);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.post("/fertilizers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.name").value("Target"));

    }
}
