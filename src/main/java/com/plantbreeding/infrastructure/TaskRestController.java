package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enumeration.HealthStatus;
import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.service.TaskService;
import com.plantbreeding.infrastructure.dto.request.CreateTaskRequestDto;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import com.plantbreeding.infrastructure.dto.response.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/tasks")
public class TaskRestController {
    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/daily")
    public ResponseEntity<GetAllTasksResponseDto> getTasksForDate(@RequestParam("date") LocalDate taskDate) {
        List<TaskDto> tasksDtos = taskService.findTasksByDate(taskDate);
        GetAllTasksResponseDto response = new GetAllTasksResponseDto(tasksDtos);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<String> addTask(@RequestBody @Valid CreateTaskRequestDto request) {
        taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task(s) added successfully");
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id,
                                              @RequestParam TaskStatus taskStatus) {
        taskService.updateTasksStatus(id, taskStatus);
        return ResponseEntity.ok("Task updated successfully");
    }

}
