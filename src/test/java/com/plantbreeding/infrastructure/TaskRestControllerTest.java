package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.domain.enums.TaskType;
import com.plantbreeding.service.TaskService;
import com.plantbreeding.controller.rest.TaskRestController;
import com.plantbreeding.dto.request.TaskDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

class TaskRestControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskRestController taskRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(taskRestController);
    }

    @Test
    void shouldReturnTasksByDate() {
        //given
        Long taskId = 1L;
        LocalDate localDate = LocalDate.of(2024, 2, 23);
        TaskDto taskDto = new TaskDto(taskId, TaskType.WATERING, "discription", localDate, TaskStatus.OVERDUE,2L);
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(taskDto);
        given(taskService.findTasksByDate(localDate)).willReturn(taskDtos);
        //when //then
        RestAssuredMockMvc.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("date", localDate.toString())
                .when()
                .get("/tasks/daily")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("tasks[0].id", equalTo(taskId.intValue()))
                .body("tasks[0].notes", equalTo("discription"))
                .body("tasks[0].status", equalTo("OVERDUE"));
    }
}
