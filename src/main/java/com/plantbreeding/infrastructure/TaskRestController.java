package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.service.TaskService;
import com.plantbreeding.infrastructure.dto.response.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@Log4j2
public class TaskRestController {
    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/daily")
    public ResponseEntity<GetAllTasksResponseDto> getTasksForDate(@RequestParam("date") LocalDate taskDate) {
        List<Task> tasks = taskService.findTasksByDate(taskDate);
        GetAllTasksResponseDto response = new GetAllTasksResponseDto(tasks);
        return ResponseEntity.ok(response);
    }

}
