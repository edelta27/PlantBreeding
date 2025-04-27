package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import java.time.LocalDate;
import com.plantbreeding.service.PlantService;
import com.plantbreeding.dto.request.PlantDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

class PlantRestControllerTest {

    @Mock
    private PlantService plantService;

    @InjectMocks
    private PlantRestController plantRestController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(plantRestController);
    }

    @Test
    void shouldReturnPlantById(){
        //given
        Long plantId = 1L;
        LocalDate localDate = LocalDate.of(2024, 2, 23);
        PlantDto plantDto = new PlantDto(plantId, "Rose", PlantType.FLOWER, localDate, HealthStatus.HEALTHY,true, "small flower", 2);
        given(plantService.getPlantById(plantId)).willReturn(plantDto);
        //when //then
        RestAssuredMockMvc.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/plants/{id}", plantId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("id", equalTo(plantId.intValue()))
                .body("name", equalTo("Rose"))
                .body("description", equalTo("small flower"))
                .body("plantType", equalTo("FLOWER"))
                .body("healthStatus", equalTo("HEALTHY"))
                .body("isAnnual", equalTo(true))
                .body("height", equalTo(2));
    }

}