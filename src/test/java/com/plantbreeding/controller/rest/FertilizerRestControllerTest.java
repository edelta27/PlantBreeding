package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.ApplicationMethod;
import com.plantbreeding.domain.enums.FertilizerType;
import com.plantbreeding.dto.request.FertilizerDto;
import com.plantbreeding.service.FertilizerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.equalTo;

class FertilizerRestControllerTest {
    @Mock
    private FertilizerService fertilizerService;

    @InjectMocks
    private FertilizerRestController fertilizerRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(fertilizerRestController);
    }
    @Test
    void shouldReturnAllFertilizers() {
        // given
        FertilizerDto fertilizerDto = new FertilizerDto(
                1L,
                "BioFertilizer",
                FertilizerType.ORGANIC,
                ApplicationMethod.GRANULATED,
                "Natural fertilizer"
        );
        List<FertilizerDto> fertilizers = List.of(fertilizerDto);
        given(fertilizerService.getAllFertilizers()).willReturn(fertilizers);

        // when // then
        RestAssuredMockMvc.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/fertilizers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("fertilizers[0].id", equalTo(1))
                .body("fertilizers[0].name", equalTo("BioFertilizer"))
                .body("fertilizers[0].type", equalTo("ORGANIC"))
                .body("fertilizers[0].usageRecommendations", equalTo("Natural fertilizer"))
                .body("fertilizers[0].applicationMethod", equalTo("GRANULATED"));
    }
}