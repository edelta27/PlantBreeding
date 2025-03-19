package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.service.TaskService;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.dto.response.GetAllTasksResponseDto;
import com.plantbreeding.dto.response.MessageResponseDto;
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
    public ResponseEntity<MessageResponseDto> addTask(@RequestBody @Valid CreateTaskRequestDto request) {
        taskService.createTask(request);
        return ResponseEntity.ok(new MessageResponseDto("Task(s) added successfully", HttpStatus.OK));
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<MessageResponseDto> updateTask(@PathVariable Long id,
                                              @RequestParam TaskStatus taskStatus) {
        taskService.updateTasksStatus(id, taskStatus);
        return ResponseEntity.ok(new MessageResponseDto("Task updated successfully", HttpStatus.OK));
    }

}
