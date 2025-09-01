package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.service.TaskService;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.dto.response.MessageResponseDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing tasks.
 * Provides endpoints for retrieving, adding and updating.
 */
@RestController
@Log4j2
@RequestMapping("/tasks")
public class TaskRestController {
    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Gets a list of tasks scheduled for the specified day in param.
     *
     * <p>This endpoint is intended to help users quickly access
     * their daily planned activities related to plants.</p>
     *
     * @return a ResponseEntity containing a list of TaskDto objects
     *         scheduled for the specified date
     */
    @GetMapping("/daily")
    public ResponseEntity<List<TaskDto>> getTasksForDate(@RequestParam("date") LocalDate taskDate) {
        List<TaskDto> tasksDtos = taskService.findTasksByDate(taskDate);
        return ResponseEntity.ok(tasksDtos);
    }

    /**
     * Adds a new task to the system.
     *
     * <p>The task is associated with a plant and contains
     * information such as description, date, and status.</p>
     *
     * @param taskRequestDto the data of the new task, provided as a {@link TaskDto}
     * @return a {@link ResponseEntity} containing the saved {@link TaskDto}
     */
    @PostMapping()
    public ResponseEntity<List<TaskDto>> addTask(@RequestBody @Valid CreateTaskRequestDto taskRequestDto) {
        List<TaskDto> newTaskDto = taskService.createTask(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTaskDto);
    }

    /**
     * Updates an existing task.
     *
     * <p>The method allows partial updates (PATCH), such as
     * changing the status or description of the task.</p>
     *
     * @param id         the unique identifier of the task to be updated
     * @param taskStatus the updated task status
     * @return a {@link ResponseEntity} containing the updated {@link TaskDto}
     */
    @PatchMapping ("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id,
                                              @RequestParam TaskStatus taskStatus) {
        TaskDto updatedTask = taskService.updateTasksStatus(id, taskStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedTask);
    }

}
